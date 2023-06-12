package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.form.CampusNewsVo2;
import com.sxdzsoft.easyresource.service.CampusNewsTmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/15 15:09
 * @PackageName:com.sxdzsoft.easyresource.handler
 * @ClassName: CampusNewsTmpHandler
 * @Description: TODO
 * @Version 1.0
 */
@Controller
public class CampusNewsTmpHandler {

    @Autowired
    private CampusNewsTmpService campusNewsTmpService;

    /**
     * @Description: 获取校园新闻从表数据
     * @data:[]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.CampusNewsTmp>
     * @Author: YangXiaoDong
     * @Date: 2023/5/16 14:12
     */
    @GetMapping("/queryCampusNewsHandlerAll")
    @ResponseBody
    public List<CampusNewsVo2> queryCampusNewsHandlerAll() {
        return campusNewsTmpService.queryCampusNewsTmp();
    }


    /**
     * @Description: 根据id判断当前数据库是否存在此条数据
     * @data:[CampusNewsTmpId]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/16 14:16
     */
    @GetMapping("/isContain")
    @ResponseBody
    public int isContain(int CampusNewsTmpId){
        int res = campusNewsTmpService.isContain(CampusNewsTmpId);
        return res;
    }

    @GetMapping("/test1")
    @ResponseBody
    public void test() {
        String sourceImagePath = "D:\\workbookupload\\2023-02-14\\41815e58-2a71-4dcd-8645-0d2e480fe540.png";
        String compressedImagePath = "e:\\test.jpg";
        int targetWidth = 800; // 目标宽度
        int targetHeight = 600; // 目标高度
        float imageQuality = 0.5f; // 图片质量

        try {
            // 读取源图片
            BufferedImage sourceImage = ImageIO.read(new File(sourceImagePath));

            // 创建缩放后的图片
            BufferedImage compressedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = compressedImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.drawImage(sourceImage, 0, 0, targetWidth, targetHeight, null);
            g2d.dispose();

            // 保存压缩后的图片
            ImageIO.write(compressedImage, "jpg", new File(compressedImagePath));

            System.out.println("图片压缩完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
