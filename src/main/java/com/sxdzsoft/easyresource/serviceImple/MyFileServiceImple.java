package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.mapper.ClazzMapper;
import com.sxdzsoft.easyresource.mapper.MyFileMapper;
import com.sxdzsoft.easyresource.mapper.UserMapper;
import com.sxdzsoft.easyresource.service.MyFileService;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName MyFileServiceImple
 * @Description TODO
 * @Author wujian
 * @Date 2022/5/31 15:29
 * @Version 1.0
 **/
@Service
public class MyFileServiceImple implements MyFileService {
    @Autowired
    private MyFileMapper myFileMapper;

    @Autowired
    private ClazzMapper clazzMapper;


    @Override
    public MyFile addFormFile(long fileSize, String store, String name, int isUse) {
        MyFile myFile = new MyFile();
        myFile.setName(name);
        myFile.setSize(fileSize);
        myFile.setStore(store);
        myFile.setIsUse(isUse);
        MyFile singFile = myFileMapper.save(myFile);
        return singFile;
    }

    @Override
    public MyFile queryFileById(int fileId) {
        return this.myFileMapper.getById(fileId);
    }

    @Override
    public int delFormFile(int fileId) {
        MyFile singFile = myFileMapper.getById(fileId);
        singFile.setIsUse(0);
        myFileMapper.save(singFile);
        return 0;
    }

    @Override
    public MyFile uploadImage(MultipartFile orgfile) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dir = format.format(new Date());
        String path = "d://tablemasterupload/" + dir;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

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
        }

        MyFile myFile = new MyFile();
        myFile.setName(oriFilename);
        myFile.setSize(fileSize);
        myFile.setStore(storePath);
        myFile.setIsUse(1);
        return myFileMapper.save(myFile);
    }

    private BufferedImage compressImage(MultipartFile file)  {
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


    @Override
    public DataTableModel<MyFile> queryByClazzId(Integer clazzId, Integer page, Integer pageSize) {
        Page<MyFile> all = myFileMapper.queryByClazzMienIdAndIsUse(clazzId, 1, PageRequest.of(page, pageSize));
        DataTableModel<MyFile> result = new DataTableModel();
        result.setData(all.getContent());
        result.setRecordsFiltered(Long.valueOf(all.getTotalElements()).intValue());
        result.setRecordsTotal(all.getNumberOfElements());
        return result;
    }

    @Override
    public List<MyFile> queryByClazzId(Integer clazzId) {
        List<MyFile> myFiles = myFileMapper.queryByClazzMienId(clazzId);
        return myFiles;
    }

    @Override
    public int updateClazzAndStatu(Integer clazzId, Integer fileId) {
        MyFile myFile = myFileMapper.getById(fileId);
        Clazz clazz = clazzMapper.getById(clazzId);
        myFile.setClazz(clazz);
        myFile.setIsUse(1);
        MyFile save = myFileMapper.save(myFile);
        if (save!=null){
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }

    @Override
    public int updateClazzMienAndStatu(Integer clazzId, Integer fileId) {
        MyFile myFile = myFileMapper.getById(fileId);
        Clazz clazz = clazzMapper.getById(clazzId);
        myFile.setClazzMien(clazz);
        myFile.setIsUse(1);
        MyFile save = myFileMapper.save(myFile);
        if (save!=null){
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }
}

