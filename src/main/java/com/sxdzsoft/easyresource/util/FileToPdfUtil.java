package com.sxdzsoft.easyresource.util;

import org.jodconverter.OfficeDocumentConverter;
import org.jodconverter.office.DefaultOfficeManagerBuilder;
import org.jodconverter.office.OfficeException;
import org.jodconverter.office.OfficeManager;

import java.io.File;
import java.util.regex.Pattern;

/**
 * @ClassName FileToPdfUtil
 * @Description 文件转PDF
 * @Author wujian
 * @Date 2022/6/1 14:17
 * @Version 1.0
 **/
public class FileToPdfUtil {
    private final static String PDF_STORE_PATH="d://upload/fileReadFor";
    public static void officeToPdf(File orgFile){
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
}
