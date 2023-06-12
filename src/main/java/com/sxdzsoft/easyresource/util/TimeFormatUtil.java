package com.sxdzsoft.easyresource.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/16 15:54
 * @PackageName:com.sxdzsoft.easyresource.util
 * @ClassName: TimeFormatUtil
 * @Description: TODO
 * @Version 1.0
 */
public class TimeFormatUtil {

    /**
     * @Description: String类型 -> date类型
     * @data:[dateString]
     * @return: java.util.Date
     * @Author: YangXiaoDong
     * @Date: 2023/5/18 15:01
     */
    public static Date convertStringToDate(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @Description: date类型 -> String类型
     * @data:[date]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/5/18 15:03
     */
    public static String covertDateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(date);
        return time;
    }
}
