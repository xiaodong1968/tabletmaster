package com.sxdzsoft.easyresource.util;

/**
 * @ClassName MyNetUtils
 * @Description 网络工具
 * @Author wujian
 * @Date 2022/4/20 16:07
 * @Version 1.0
 **/
public class MyNetUtils {
    /**
     * @Description TODO
     * @Author 获取请求主机的IP地址
     * @Date 16:08 2022/4/20
     * @Params [request]
     * @Return
     **/
    public static String getRemoteHost(javax.servlet.http.HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }
}
