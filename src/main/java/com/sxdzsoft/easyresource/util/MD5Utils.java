package com.sxdzsoft.easyresource.util;

import java.security.MessageDigest;
import java.util.Random;

/**
 * @ClassName MD5Utils
 * @Description 使用MD5对字符串加解密
 * @Author wujian
 * @Date 2022/4/25 15:06
 * @Version 1.0
 **/
public class MD5Utils {
   /**
    * @Description byte[]字节数组 转换成 十六进制字符串
    * @Author wujian
    * @Date 15:07 2022/4/25
    * @Params [arr]
    * @Return
    **/
    private static String hex(byte[] arr) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arr.length; ++i) {
            sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }
    /**
     * @Description MD5加密,并把结果由字节数组转换成十六进制字符串
     * @Author wujian
     * @Date 15:08 2022/4/25
     * @Params [str]
     * @Return
     **/
    private static String md5Hex(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(str.getBytes());
            return hex(digest);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

   /**
    * @Description 生成盐值加密的MD5
    * @Author wujian
    * @Date 15:08 2022/4/25
    * @Params [password]
    * @Return
    **/
    public static String createSaltMD5(String password) {
        // 生成一个16位的随机数
        Random random = new Random();
        StringBuilder sBuilder = new StringBuilder(16);
        sBuilder.append(random.nextInt(99999999)).append(random.nextInt(99999999));
        int len = sBuilder.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sBuilder.append("0");
            }
        }
        // 生成最终的加密盐
        String salt = sBuilder.toString();
        password = md5Hex(password + salt);
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = password.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return String.valueOf(cs);
    }
    /**
     *
     * Author:WuJian
     * Description:密码验证
     * @param password 前台传递明文密码
     * @param md5str 数据库存储密码
     * @return
     * boolean
     * MD5Utils.java
     * LastModify:2020年2月3日
     */
    public static boolean CheckSaltverifyMD5(String password, String md5str) {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5str.charAt(i);
            cs1[i / 3 * 2 + 1] = md5str.charAt(i + 2);
            cs2[i / 3] = md5str.charAt(i + 1);
        }
        String Salt = new String(cs2);
        return md5Hex(password + Salt).equals(String.valueOf(cs1));
    }
}
