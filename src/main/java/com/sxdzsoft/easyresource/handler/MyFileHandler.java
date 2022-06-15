package com.sxdzsoft.easyresource.handler;
import com.sxdzsoft.easyresource.domain.MyFile;
import com.sxdzsoft.easyresource.domain.MyFormItem;
import com.sxdzsoft.easyresource.domain.User;
import com.sxdzsoft.easyresource.service.MyFileService;
import com.sxdzsoft.easyresource.service.MyFormService;
import com.sxdzsoft.easyresource.util.FileToPdfUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName MyFileHandler
 * @Description 我的文件处理器
 * @Author wujian
 * @Date 2022/5/31 15:27
 * @Version 1.0
 **/
@Controller
public class MyFileHandler {
    @Autowired
    private MyFormService myFormService;
    @Autowired
    private MyFileService myFileService;
    @Autowired
    private HttpSession httpSession;
    /**
     * @Description 上传我的任务文件对话框
     * @Author wujian
     * @Date 15:30 2022/5/31
     * @Params [itemId, model]
     * @Return
     **/
    @GetMapping(path="/uploadTaskFileDialog")
    public String uploadTaskFileDialog(int itemId, Model model){
        MyFormItem item=this.myFormService.queryMyFormItemById(itemId);
        List<MyFile> files=new ArrayList<MyFile>();
        for(MyFile file:item.getFiles()){
            if(file.getIsUse()==1){
                files.add(file);
            }
        }
        item.setFiles(files);
        model.addAttribute("item",item);
        return "pages/filemanage/uploadTaskFileDialog";
    }
    /**
     * @Description 查看任务文件
     * @Author wujian
     * @Date 18:13 2022/6/1
     * @Params [itemId, model]
     * @Return
     **/
    @GetMapping(path="/seeTaskFileDialog")
    public String seeTaskFileDialog(int itemId, Model model){
        MyFormItem item=this.myFormService.queryMyFormItemById(itemId);
        List<MyFile> files=new ArrayList<MyFile>();
        for(MyFile file:item.getFiles()){
            if(file.getIsUse()==1){
                files.add(file);
            }
        }
        item.setFiles(files);
        User owner=item.getMyForm().getOwner();
        User currentUser=(User)this.httpSession.getAttribute("userinfo");

        MyFormItem zp=this.myFormService.queryMyFormItemByTypeIsAndMyFormIdIsAndRowIs(2,item.getMyForm().getId(),item.getRow());
        MyFormItem fh=this.myFormService.queryMyFormItemByTypeIsAndMyFormIdIsAndRowIs(3,item.getMyForm().getId(),item.getRow());
        model.addAttribute("zp",zp);
        model.addAttribute("fh",fh);
        if(fh!=null){
            //判断当前登录用户是否为当前审阅用户，如果是，不显示复核按钮，即不能为自己复核评分
            if(owner.getId().intValue()==currentUser.getId().intValue()){
                model.addAttribute("fhBtn",0);
            }else{
                model.addAttribute("fhBtn",1);
            }
        }
        model.addAttribute("owner",owner);
        model.addAttribute("item",item);
        return "pages/filemanage/seeTaskFileDialog";
    }
    /**
     * @Description layui组件上传文件
     * @Author wujian
     * @Date 17:22 2022/5/31
     * @Params [request]
     * @Return
     **/
    @RequestMapping(path="/fileUploadForLayUi")
    @ResponseBody
    public Map<String,String> fileUploadForLayUi(HttpServletRequest request,int itemId) throws IOException {
        MultipartHttpServletRequest mreq=(MultipartHttpServletRequest)request;
        boolean needToCovertPdf=false;
        String path="d://upload/";
        MultipartFile orgfile=mreq.getFile("file");
        String orgFile=orgfile.getOriginalFilename();
        long fileSize=orgfile.getSize();
        String prefix=orgFile.substring(orgFile.lastIndexOf("."));
        File file=null;
        String newname=UUID.randomUUID().toString()+prefix;
        String result="";
        String preReadStore=null;//预览文件
        int fileType=2;
        //如果是视频上传
        if(prefix.equalsIgnoreCase(".mp4")) {
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            String dir=format.format(new Date());
            result=dir+"/videos/"+newname;
            path=path+dir+"/videos";
            file=new File(path);
            if(!file.exists()) {
                file.mkdirs();
            }
            file=new File(path,newname);
            if(!file.exists()) {
                file.createNewFile();
            }
            fileType=7;
        }
        else if(prefix.equals(".rar")||prefix.equals(".zip")||prefix.equals(".doc")||prefix.equals(".docx")||prefix.equals(".xlsx")||prefix.equals(".xls")||prefix.equals(".text")||prefix.equals(".pdf")||prefix.equals(".ppt")||prefix.equals(".pptx")) {
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            String dir=format.format(new Date());
            result=dir+"/files/"+newname;
            path=path+dir+"/files";
            file=new File(path);
            if(!file.exists()) {
                file.mkdirs();
            }
            file=new File(path,newname);
            if(!file.exists()) {
                file.createNewFile();
            }
            switch (prefix){
                case ".rar":
                    fileType=1;
                    break;
                case ".zip":
                    fileType=1;
                    break;
                case ".pdf":
                    fileType=2;
                    break;
                case ".doc":
                    fileType=3;
                    preReadStore=newname.substring(0,newname.lastIndexOf("."))+".pdf";
                    needToCovertPdf=true;
                    break;
                case ".docx":
                    fileType=3;
                    preReadStore=newname.substring(0,newname.lastIndexOf("."))+".pdf";
                    needToCovertPdf=true;
                    break;
                case ".xls":
                    fileType=4;
                    preReadStore=newname.substring(0,newname.lastIndexOf("."))+".pdf";
                    needToCovertPdf=true;
                    break;
                case ".xlsx":
                    fileType=4;
                    preReadStore=newname.substring(0,newname.lastIndexOf("."))+".pdf";
                    needToCovertPdf=true;
                    break;
                case ".ppt":
                    fileType=5;
                    preReadStore=newname.substring(0,newname.lastIndexOf("."))+".pdf";
                    needToCovertPdf=true;
                    break;
                case ".pptx":
                    fileType=5;
                    preReadStore=newname.substring(0,newname.lastIndexOf("."))+".pdf";
                    needToCovertPdf=true;
                    break;
                case ".txt":
                    fileType=6;
                    preReadStore=newname.substring(0,newname.lastIndexOf("."))+".pdf";
                    needToCovertPdf=true;
                    break;

            }

        }
        else if(prefix.equals(".gif")||prefix.equals(".jpg")||prefix.equals(".jpeg")||prefix.equals(".png")){
            fileType=0;
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            String dir=format.format(new Date());
            result=dir+"/imgs/"+newname;
            path=path+dir+"/imgs";
            file=new File(path);
            if(!file.exists()) {
                file.mkdirs();
            }
            file=new File(path,newname);
            if(!file.exists()) {
                file.createNewFile();
            }
        }
        else{
            fileType=-1;
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            String dir=format.format(new Date());
            result=dir+"/other/"+newname;
            path=path+dir+"/other";
            file=new File(path);
            if(!file.exists()) {
                file.mkdirs();
            }
            file=new File(path,newname);
            if(!file.exists()) {
                file.createNewFile();
            }
        }
        FileUtils.writeByteArrayToFile(file, orgfile.getBytes());
        if(needToCovertPdf){
            FileToPdfUtil.officeToPdf(file);
        }
        Map<String,String> res=new HashMap<String,String>();
        res.put("data", result+"@"+orgFile);

        User owner=(User)this.httpSession.getAttribute("userinfo");
        this.myFileService.addFormFile(fileType,fileSize,itemId,preReadStore,result,orgFile,owner);
        return  res;
    }
    /**
     * @Description 预览文件
     * @Author wujian
     * @Date 15:31 2022/6/1
     * @Params [res, fileId]
     * @Return
     **/
    @GetMapping(path="/readFile/{fileId}")
    public String  readFile(HttpServletResponse res , @PathVariable int  fileId,Model model) throws IOException {
        MyFile file=this.myFileService.queryFileById(fileId);
        InputStream in = null;
        OutputStream out = null;
        try {
            String path;
            if(file.getStore().endsWith(".pdf")){
                path="d://upload/"+file.getStore();
            }
            else{
                path="d://upload/fileReadFor/"+file.getPreReadFileStore();
            }
            in = new FileInputStream(path);
            res.setContentType("application/pdf");
            out = res.getOutputStream();
            byte[] b = new byte[1024];
            int len = 0;
            while((len = in.read(b)) != -1){
                out.write(b);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            model.addAttribute("fileId",fileId);
            return "pages/filemanage/cantPre";
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("fileId",fileId);
            return "pages/filemanage/cantPre";

        }finally {
            if(in != null){
                in.close();
               }
               if(out != null){
                       out.close();
               }
        }
        return null;
    }
    /**
     * @Description 删除表单文件
     * @Author wujian
     * @Date 17:53 2022/6/1
     * @Params [fileId]
     * @Return
     **/
    @PostMapping(path="/delFormFile")
    @ResponseBody
    public int delFormFile(int fileId){
       return  this.myFileService.delFormFile(fileId);
    }
    /**
     * @Description 读取图片
     * @Author wujian
     * @Date 14:10 2022/6/2
     * @Params [fileId, response]
     * @Return
     **/
    @GetMapping(path="/readImage")
    public String readImage(int fileId,HttpServletResponse response,Model model){
        MyFile myFile=this.myFileService.queryFileById(fileId);
        String store=myFile.getStore();
        String path="d://upload/"+store;
        File file=new File(path);
        FileInputStream fins=null;
        OutputStream out=null;
        try {
             fins=FileUtils.openInputStream(file);
            int i=fins.available();
            byte[] temp = new byte[(int) i];
            fins.read(temp, 0, (int) i);
            String prefix=file.getName().substring(file.getName().lastIndexOf(".")+1);
            response.setContentType("image/"+prefix);
             out = response.getOutputStream();
            out.write(temp);
        } catch (IOException e) {
            model.addAttribute("fileId",fileId);

            e.printStackTrace();
            return "pages/filemanage/cantPre";
        }finally {
            try {
                if(fins != null){
                        fins.close();
                }
                if(out != null){
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    /**
     * @Description 读取视频
     * @Author wujian
     * @Date 14:21 2022/6/2
     * @Params [fileId, response, request]
     * @Return
     **/
    @GetMapping("/readVideo")
    public String showVideo2( int fileId,HttpServletResponse response,HttpServletRequest request,Model model) {
        MyFile myFile=this.myFileService.queryFileById(fileId);
        String store=myFile.getStore();
        String path="d://upload/"+store;
        BufferedInputStream bis = null;

        try {
            File file=new File(path);

            if (file.exists()) {
                long p = 0L;

                long toLength = 0L;

                long contentLength = 0L;

                int rangeSwitch = 0;

                long fileLength;

                String rangBytes = "";

                fileLength = file.length();

                // get file content

                InputStream ins = new FileInputStream(file);

                bis = new BufferedInputStream(ins);

                // tell the client to allow accept-ranges

                response.reset();

                response.setHeader("Accept-Ranges", "bytes");

                // client requests a file block download start byte

                String range = request.getHeader("Range");

                if (range != null && range.trim().length() > 0 && !"null".equals(range)) {
                    response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);

                    rangBytes = range.replaceAll("bytes=", "");

                    if (rangBytes.endsWith("-")) { // bytes=270000-

                        rangeSwitch = 1;

                        p = Long.parseLong(rangBytes.substring(0, rangBytes.indexOf("-")));

                        contentLength = fileLength - p; // 客户端请求的是270000之后的字节(包括bytes下标索引为270000的字节)

                    } else { // bytes=270000-320000

                        rangeSwitch = 2;

                        String temp1 = rangBytes.substring(0, rangBytes.indexOf("-"));

                        String temp2 = rangBytes.substring(rangBytes.indexOf("-") + 1, rangBytes.length());

                        p = Long.parseLong(temp1);

                        toLength = Long.parseLong(temp2);

                        contentLength = toLength - p + 1; // 客户端请求的是 270000-320000 之间的字节

                    }

                } else {
                    contentLength = fileLength;

                }

                // 如果设设置了Content-Length，则客户端会自动进行多线程下载。如果不希望支持多线程，则不要设置这个参数。

                // Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]

                response.setHeader("Content-Length", new Long(contentLength).toString());

                // 断点开始

                // 响应的格式是:

                // Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]

                if (rangeSwitch == 1) {
                    String contentRange = new StringBuffer("bytes ").append(new Long(p).toString()).append("-")

                            .append(new Long(fileLength - 1).toString()).append("/")

                            .append(new Long(fileLength).toString()).toString();

                    response.setHeader("Content-Range", contentRange);

                    bis.skip(p);

                } else if (rangeSwitch == 2) {
                    String contentRange = range.replace("=", " ") + "/" + new Long(fileLength).toString();

                    response.setHeader("Content-Range", contentRange);

                    bis.skip(p);

                } else {
                    String contentRange = new StringBuffer("bytes ").append("0-").append(fileLength - 1).append("/")

                            .append(fileLength).toString();

                    response.setHeader("Content-Range", contentRange);

                }


                response.setContentType("application/octet-stream");

                response.addHeader("Content-Disposition", "attachment;filename=" + file.getName());

                OutputStream out = response.getOutputStream();

                int n = 0;

                long readLength = 0;

                int bsize = 1024;

                byte[] bytes = new byte[bsize];

                if (rangeSwitch == 2) {
                    // 针对 bytes=27000-39000 的请求，从27000开始写数据

                    while (readLength <= contentLength - bsize) {
                        n = bis.read(bytes);

                        readLength += n;

                        out.write(bytes, 0, n);

                    }

                    if (readLength <= contentLength) {
                        n = bis.read(bytes, 0, (int) (contentLength - readLength));

                        out.write(bytes, 0, n);

                    }

                } else {
                    while ((n = bis.read(bytes)) != -1) {
                        out.write(bytes, 0, n);

                    }

                }

                out.flush();

                out.close();

                bis.close();

            }

        } catch (IOException ie) {
            model.addAttribute("fileId",fileId);
            return "pages/filemanage/cantPre";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("fileId",fileId);
            return "pages/filemanage/cantPre";

        }
        return null;
    }
    /**
     * @Description 播放多媒体文件
     * @Author wujian
     * @Date 14:29 2022/6/2
     * @Params [fileId, mediaType:0图片 1视频, model]
     * @Return
     **/
    @GetMapping(path="/playMediaDialog")
    public String playMediaDialog(int fileId,int mediaType,Model model){
        model.addAttribute("fileId",fileId);
        model.addAttribute("mediaType",mediaType);
        return "pages/filemanage/playMediaDialog";
    }
    /**
     * @Description 文件下载
     * @Author wujian
     * @Date 16:34 2022/6/2
     * @Params [fileId, response]
     * @Return
     **/
    @GetMapping(path="/downLoadFile")
    public HttpServletResponse downLoadFile(int fileId, HttpServletResponse response) {
        try {
            MyFile myFile=this.myFileService.queryFileById(fileId);
            String path="d://upload/"+myFile.getStore();
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = myFile.getName();
            InputStream input = new FileInputStream(path);
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("utf-8"),"ISO-8859-1"));
           // response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = response.getOutputStream();
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Length", ""+file.length());
            int length=0;
            byte[] buffer=new byte[1024];
            while((length=input.read(buffer))!=-1){
                toClient.write(buffer,0,length);
            }
            input.close();
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    /**
     * @Description 无法预览文件页面跳转
     * @Author wujian
     * @Date 11:44 2022/6/14
     * @Params []
     * @Return
     **/
    @GetMapping(path="/cantPre")
    public String cantPre(int fileId,Model model){
        model.addAttribute("fileId",fileId);
        return "pages/filemanage/cantPre";
    }
    /**
     * @Description 无文件页面
     * @Author wujian
     * @Date 12:41 2022/6/15
     * @Params [fileId, model]
     * @Return
     **/
    @GetMapping(path="/noFile")
    public String noFile(){
        return "pages/filemanage/noFile";
    }
}
