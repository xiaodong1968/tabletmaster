package com.sxdzsoft.easyresource.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author YangXiaoDong
 * @Date 2023/4/5 14:30
 * @PackageName:com.sxdzsoft.easyresource.util
 * @ClassName: TimeUtil
 * @Description: TODO
 * @Version 1.0
 */
public class TimeUtil {

    /**
     * @Description: 获取当前时间
     * @data:[]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/6/11 18:14
     */
    public static Date getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        return currentDate;
    }


}

