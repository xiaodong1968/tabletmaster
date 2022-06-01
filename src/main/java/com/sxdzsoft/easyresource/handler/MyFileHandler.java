package com.sxdzsoft.easyresource.handler;
import com.sxdzsoft.easyresource.domain.MyFile;
import com.sxdzsoft.easyresource.domain.MyFormItem;
import com.sxdzsoft.easyresource.domain.User;
import com.sxdzsoft.easyresource.service.MyFileService;
import com.sxdzsoft.easyresource.service.MyFormService;
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
        String path="d://upload/";
        MultipartFile orgfile=mreq.getFile("file");
        String orgFile=orgfile.getOriginalFilename();
        long fileSize=orgfile.getSize();
        String prefix=orgFile.substring(orgFile.lastIndexOf("."));
        File file=null;
        String newname=UUID.randomUUID().toString()+prefix;
        String result="";
        //如果是视频上传
        if(prefix.equals(".mp4")) {
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
        }
        else if(prefix.equals(".doc")||prefix.equals(".docx")||prefix.equals(".xls")) {
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
        }
        else {
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
        FileUtils.writeByteArrayToFile(file, orgfile.getBytes());
        Map<String,String> res=new HashMap<String,String>();
        res.put("data", result+"@"+orgFile);
        int fileType=2;
        User owner=(User)this.httpSession.getAttribute("userinfo");
        this.myFileService.addFormFile(fileType,fileSize,itemId,result,orgFile,owner);
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
    public void readFile(HttpServletResponse res , @PathVariable int  fileId) throws IOException {
        MyFile file=this.myFileService.queryFileById(fileId);
        InputStream in = null;
        OutputStream out = null;
        try {
            String path;
            if(file.getStore().endsWith(".pdf")){
                path="d://upload/"+file.getStore();
            }
            else{
                path="d://upload/fileReadFor/"+file.getName();
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
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(in != null){
                in.close();
               }
               if(out != null){
                       out.close();
               }
        }
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
}
