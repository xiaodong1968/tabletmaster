package com.sxdzsoft.easyresource.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/13 17:10
 * @PackageName:com.sxdzsoft.easyresource.util
 * @ClassName: IPRangeChecker
 * @Description: TODO
 * @Version 1.0
 */


public class IPRangeChecker {

    public static boolean isIPInRange(String ipAddress, String startRange, String endRange) {
        try {
            InetAddress ip = InetAddress.getByName(ipAddress);
            InetAddress start = InetAddress.getByName(startRange);
            InetAddress end = InetAddress.getByName(endRange);
            return isInRange(ip, start, end);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isInRange(InetAddress ip, InetAddress start, InetAddress end) {
        long ipValue = ipToLong(ip);
        long startValue = ipToLong(start);
        long endValue = ipToLong(end);

        return ipValue >= startValue && ipValue <= endValue;
    }

    public static long ipToLong(InetAddress ip) {
        byte[] octets = ip.getAddress();
        long result = 0;
        for (byte octet : octets) {
            result <<= 8;
            result |= octet & 0xff;
        }
        return result;
    }


}
