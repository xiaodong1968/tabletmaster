package com.sxdzsoft.easyresource.util;

import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jodconverter.DocumentConverter;
import org.jodconverter.OfficeDocumentConverter;
import org.jodconverter.document.DefaultDocumentFormatRegistry;
import org.jodconverter.office.DefaultOfficeManagerBuilder;
import org.jodconverter.office.ExternalOfficeManagerBuilder;
import org.jodconverter.office.OfficeException;
import org.jodconverter.office.OfficeManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.regex.Pattern;

/**
 * @ClassName FileToPdfUtil
 * @Description 文件转PDF
 * @Author wujian
 * @Date 2022/6/1 14:17
 * @Version 1.0
 **/
@Service
public class FileToPdfUtil {
    @Resource
    private DocumentConverter documentConverter;
    private final static String PDF_STORE_PATH="d://upload/fileReadFor";
    /**
     * @Description 使用libreoffice进行文件格式转换
     * (用于EXCEL转换，这里的EXCEL文件是指源文件复制并调整版式后的副本，该副本存储在预览文件目录中)
     * @Author wujian
     * @Date 20:37 2022/6/16
     * @Params [in]
     * @Return
     **/
    public String  officeToPdfWithLibreOffice2(File in){
        File excelFile=new File(FileToPdfUtil.PDF_STORE_PATH+"/"+in.getName());
        int prefixIndex=in.getName().lastIndexOf(".");
        String orgName=in.getName().substring(0,prefixIndex)+".pdf";
        String after_convert_file_path=FileToPdfUtil.PDF_STORE_PATH+"/"+ orgName;
        File outputFile = new File(after_convert_file_path);
        try {
            documentConverter.convert(excelFile).as(DefaultDocumentFormatRegistry.HTML).to(outputFile).as(DefaultDocumentFormatRegistry.PDF).execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "OK";
    }
    /**
     * @Description 使用libreoffice进行文件格式转换
     * @Author wujian
     * @Date 11:44 2022/6/16
     * @Params []
     * @Return
     **/
    public String  officeToPdfWithLibreOffice(File in){
        int prefixIndex=in.getName().lastIndexOf(".");
        String orgName=in.getName().substring(0,prefixIndex)+".pdf";
        String after_convert_file_path=FileToPdfUtil.PDF_STORE_PATH+"/"+ orgName;
        File outputFile = new File(after_convert_file_path);
        try {
            documentConverter.convert(in).as(DefaultDocumentFormatRegistry.HTML).to(outputFile).as(DefaultDocumentFormatRegistry.PDF).execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "OK";
    }
    /**
     * @Description 使用OpenOffice将office文件转换成pdf文件
     * @Author wujian
     * @Date 20:36 2022/6/16
     * @Params [orgFile]
     * @Return
     **/
    public  void officeToPdf(File orgFile){
        OfficeManager officeManager = null;
        int prefixIndex=orgFile.getName().lastIndexOf(".");
        String orgName=orgFile.getName().substring(0,prefixIndex)+".pdf";
        String after_convert_file_path=FileToPdfUtil.PDF_STORE_PATH+"/"+ orgName;
        File outputFile = new File(after_convert_file_path);
        //启动openOffice
         officeManager = getOfficeManager();
         OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
        try {
            converter.convert(orgFile,outputFile);
        } catch (OfficeException e) {
            e.printStackTrace();
        }finally {
            if (officeManager != null) {
                try {
                    officeManager.stop();
                } catch (OfficeException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * @Description 启动OpenOffice
     * @Author wujian
     * @Date 9:38 2022/6/2
     * @Params []
     * @Return
     **/
    public static OfficeManager getOfficeManager(){
       DefaultOfficeManagerBuilder builder = new DefaultOfficeManagerBuilder();
       builder.setOfficeHome(getOfficeHome());
       OfficeManager officeManager = builder.build();
       try {
               officeManager.start();
             } catch (OfficeException e) {
                   //打印日志
                 System.out.println("start openOffice Fail!");
               e.printStackTrace();
            }
             return officeManager;
    }
    /**
     * @Description 获取OpenOffice安装目录
     * @Author wujian
     * @Date 9:37 2022/6/2
     * @Params []
     * @Return
     **/
    public static String getOfficeHome(){
        String osName=System.getProperty("os.name");
        if(Pattern.matches("Windows.*",osName)){
            return "C:/Program Files (x86)/OpenOffice 4";
        }
        return null;
    }
    /**
     * @Description 读取源文件创建新的EXCEL文件，并设置新的EXCEL文件的版式
     * @Author wujian
     * @Date 20:35 2022/6/16
     * @Params [orgFile]
     * @Return
     **/
    public  void setExcelScale(File orgFile) {
        String orgName=orgFile.getName();
        String after_convert_file_path=FileToPdfUtil.PDF_STORE_PATH+"/"+ orgName;
        File outputFile = new File(after_convert_file_path);
        //读取excel文件
        XSSFWorkbook workbook=null;
        OutputStream fos = null;
        try {
            workbook = new XSSFWorkbook(new FileInputStream(orgFile));

            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {//获取每个Sheet表
                XSSFSheet sheet = workbook.getSheetAt(i);
                //打印设置
                XSSFPrintSetup print = sheet.getPrintSetup();
                print.setLandscape(true); // 打印方向，true：横向，false：纵向(默认)
                print.setFitHeight((short)1);//设置0时高度为自动分页
                print.setFitWidth((short)1);//设置宽度为一页
                print.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE); //纸张类型
//                print.setScale((short)50);//自定义缩放①，此处100为无缩放
                //启用“适合页面”打印选项的标志
                sheet.setFitToPage(true);
            }
            // Excel文件生成后存储的位置。
            File file = new File(after_convert_file_path);
            fos = new FileOutputStream(file);
            workbook.write(fos);
        } catch (Exception e) {

        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
