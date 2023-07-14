package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.domain.DataTableModel;
import com.sxdzsoft.easyresource.domain.MyFile;
import com.sxdzsoft.easyresource.form.ResultVo;
import com.sxdzsoft.easyresource.aspect.IPCheck;
import com.sxdzsoft.easyresource.service.MyFileService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    private MyFileService myFileService;

    /**
     * @Description: 根据班级id查询班级风采
     * @data:[clazzId]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.MyFile>
     * @Author: YangXiaoDong
     * @Date: 2023/5/19 11:09
     */
    @GetMapping("/queryByClazzId")
    @ResponseBody
    @IPCheck
    public DataTableModel<MyFile> queryByClazzId(Integer clazzId, Integer page, Integer pageSize) {
        return myFileService.queryByClazzId(clazzId, page, pageSize);
    }

    /**
     * @Description: 判断班级风采是否为空
     * @data:[clazzId]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.MyFile>
     * @Author: YangXiaoDong
     * @Date: 2023/7/14 17:50
     */
    @GetMapping("/isNull")
    @ResponseBody
    @IPCheck
    public List<MyFile> isNull(Integer clazzId){
        List<MyFile> myFiles = myFileService.queryByClazzId(clazzId);
        return myFiles;
    }

    /**
     * @Description: 更改照片状态以及从属班级
     * @data:[clazzId, fileId]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/23 16:37
     */
    @PostMapping("/updateClazzAndStatu")
    @ResponseBody
    public int updateClazzAndStatu(Integer clazzId, Integer fileId) {
        int res = myFileService.updateClazzAndStatu(clazzId, fileId);
        return res;
    }


    /**
     * @Description: 改变文件照片(班级风采)的状态
     * @data:[clazzId, fileId]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/24 9:31
     */
    @PostMapping("/updateClazzMienAndStatu")
    @ResponseBody
    public int updateClazzMienAndStatu(Integer clazzId, Integer fileId) {
        int res = myFileService.updateClazzMienAndStatu(clazzId, fileId);
        return res;
    }


    /**
     * @Description: 图片上传
     * @data:[request]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/2/21 10:13
     */
    @PostMapping("/uploadImage")
    @ResponseBody
    public ResultVo uploadImage(HttpServletRequest request) {
        MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) request;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dir = format.format(new Date());
        String path = "d://tablemasterupload/" + dir;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        MultipartFile orgfile = mreq.getFile("file");

        // 压缩图片
        BufferedImage compressedImage = compressImage(orgfile);

        // 获取文件大小
        long fileSize = orgfile.getSize();

        // 获取文件名称
        String oriFilename = orgfile.getOriginalFilename();

        // 保存压缩后的图片到服务器
        String originalFilename = orgfile.getOriginalFilename();
        String prefix = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        String newname = UUID.randomUUID().toString() + prefix;
        String storePath = dir + "/" + newname;
        try {
            ImageIO.write(compressedImage, prefix.substring(1), new File(path + "/" + newname));
        } catch (IOException e) {
            e.printStackTrace();
            return ResultVo.fail("失败");
        }

        MyFile singFile = this.myFileService.addFormFile(fileSize, storePath, oriFilename, 0);
        return new ResultVo(200, "成功", null, singFile);
    }

    private BufferedImage compressImage(MultipartFile file){
        // 读取源图片
        BufferedImage sourceImage = null;
        try {
            sourceImage = ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 目标尺寸
        int targetWidth = 800;
        int targetHeight = 600;

        // 创建缩放后的图片
        BufferedImage compressedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = compressedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.drawImage(sourceImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();

        return compressedImage;
    }



    /**
     * @Description 删除表单文件
     * @Author wujian
     * @Date 17:53 2022/6/1
     * @Params [fileId]
     * @Return
     **/
    @PostMapping(path = "/delFormFile")
    @ResponseBody
    public int delFormFile(int fileId) {
        return this.myFileService.delFormFile(fileId);
    }

    /**
     * @Description 读取图片
     * @Author wujian
     * @Date 14:10 2022/6/2
     * @Params [fileId, response]
     * @Return
     **/
    @GetMapping(path = "/readImage")
    public String readImage(int fileId, HttpServletResponse response, Model model) {
        MyFile myFile = this.myFileService.queryFileById(fileId);
        String store = myFile.getStore();
        String path = "d://tablemasterupload/" + store;
        File file = new File(path);
        FileInputStream fins = null;
        OutputStream out = null;
        try {
            fins = FileUtils.openInputStream(file);
            int i = fins.available();
            byte[] temp = new byte[(int) i];
            fins.read(temp, 0, (int) i);
            String prefix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            response.setContentType("image/" + prefix);
            out = response.getOutputStream();
            out.write(temp);
        } catch (IOException e) {
            model.addAttribute("fileId", fileId);
            e.printStackTrace();
            return "pages/filemanage/cantPre";
        } finally {
            try {
                if (fins != null) {
                    fins.close();
                }
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @GetMapping(path = "/readHeadImage")
    public String readHeadImage(String headImageAddresss, HttpServletResponse response, Model model) {
        String path = headImageAddresss;
        File file = new File(path);
        FileInputStream fins = null;
        OutputStream out = null;
        try {
            fins = FileUtils.openInputStream(file);
            int i = fins.available();
            byte[] temp = new byte[(int) i];
            fins.read(temp, 0, (int) i);
            String prefix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            response.setContentType("image/" + prefix);
            out = response.getOutputStream();
            out.write(temp);
        } catch (IOException e) {
//            model.addAttribute("fileId",fileId);

            e.printStackTrace();
            return "pages/filemanage/cantPre";
        } finally {
            try {
                if (fins != null) {
                    fins.close();
                }
                if (out != null) {
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
    public String showVideo2(int fileId, HttpServletResponse response, HttpServletRequest request, Model model) {
        MyFile myFile = this.myFileService.queryFileById(fileId);
        String store = myFile.getStore();
        String path = "d://upload/" + store;
        BufferedInputStream bis = null;

        try {
            File file = new File(path);

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
                    response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);

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
            model.addAttribute("fileId", fileId);
            return "pages/filemanage/cantPre";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("fileId", fileId);
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
    @GetMapping(path = "/playMediaDialog")
    public String playMediaDialog(int fileId, int mediaType, Model model) {
        model.addAttribute("fileId", fileId);
        model.addAttribute("mediaType", mediaType);
        return "pages/filemanage/playMediaDialog";
    }

    /**
     * @Description 文件下载
     * @Author wujian
     * @Date 16:34 2022/6/2
     * @Params [fileId, response]
     * @Return
     **/
    @GetMapping(path = "/downLoadFile")
    public HttpServletResponse downLoadFile(int fileId, HttpServletResponse response) {
        try {
            MyFile myFile = this.myFileService.queryFileById(fileId);
            String path = "d://upload/" + myFile.getStore();
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = myFile.getName();
            InputStream input = new FileInputStream(path);
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("utf-8"), "ISO-8859-1"));
            // response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = response.getOutputStream();
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Length", "" + file.length());
            int length = 0;
            byte[] buffer = new byte[1024];
            while ((length = input.read(buffer)) != -1) {
                toClient.write(buffer, 0, length);
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
    @GetMapping(path = "/cantPre")
    public String cantPre(int fileId, Model model) {
        model.addAttribute("fileId", fileId);
        return "pages/filemanage/cantPre";
    }

    /**
     * @Description 无文件页面
     * @Author wujian
     * @Date 12:41 2022/6/15
     * @Params [fileId, model]
     * @Return
     **/
    @GetMapping(path = "/noFile")
    public String noFile() {
        return "pages/filemanage/noFile";
    }


    /**
     * @Description 读取系统文件
     * @Author wujian
     * @Date 21:30 2022/6/17
     * @Params [res, fileId, model]
     * @Return
     **/
    @GetMapping(path = "/readSystemFile/{fileName}")
    public void readSystemFile(HttpServletResponse res, @PathVariable String fileName, Model model) throws IOException {
        InputStream in = null;
        OutputStream out = null;
        try {
            String path;
            path = "d://upload/systemFile/" + fileName + ".pdf";
            in = new FileInputStream(path);
            res.setContentType("application/pdf");
            out = res.getOutputStream();
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = in.read(b)) != -1) {
                out.write(b);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }


    @GetMapping("/getphoto")
    public ResponseEntity<Resource> getPhoto() {
        // 根据你的逻辑从服务器上获取照片的路径或二进制数据
        // 这里使用示例代码，假设照片存储在本地文件系统中
        MyFile myFile = myFileService.queryFileById(31);
        String store = myFile.getStore();
        String path = "d://tablemasterupload/" + store;
        Path photoPath = Paths.get(path);

        try {
            Resource resource = new UrlResource(photoPath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header("Content-Disposition", "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            // 处理URL异常
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

}
