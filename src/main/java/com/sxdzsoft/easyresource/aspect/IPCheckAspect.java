package com.sxdzsoft.easyresource.aspect;

import com.sxdzsoft.easyresource.domain.BlackList;
import com.sxdzsoft.easyresource.domain.WhiteList;
import com.sxdzsoft.easyresource.service.BlackListService;
import com.sxdzsoft.easyresource.service.WhiteListService;
import com.sxdzsoft.easyresource.util.IPRangeChecker;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/22 11:13
 * @PackageName:com.sxdzsoft.easyresource.aspect
 * @ClassName: IPCheckAspect
 * @Description: TODO
 * @Version 1.0
 */
@Aspect
@Component
@Order(1)
public class IPCheckAspect {

    @Autowired
    private WhiteListService whiteListService;

    @Autowired
    private BlackListService blackListService;

    @Autowired
    private HttpServletRequest request;

    private static final Logger log = LoggerFactory.getLogger("operationLog");

    @Around("@annotation(com.sxdzsoft.easyresource.aspect.IPCheck)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String ipAddress = getClientIP();
        WhiteList whiteList = whiteListService.queryWhite();
        BlackList blackList = blackListService.queryBlack();
        // 在这里可以根据获取到的IP地址进行判断和处理

        if (whiteList == null) {
            // 没有设置白名单，拒绝握手
            log.info("【白名单暂未设置】" + ipAddress + " 尝试请求");
            return null;
        }

        // 检查黑名单
        if (blackList != null && isIPInList(ipAddress, blackList.getIpRanges())) {
            // IP 在黑名单内，拒绝握手
            log.info("IP " + ipAddress + " 在黑名单内并尝试请求，已拒绝请求");
            return null;
        }

        // 检查白名单
        if (!isIPInList(ipAddress, whiteList.getIpRanges())) {
            // IP 不在白名单内，拒绝握手
            log.info("IP " + ipAddress + " 未在白名单内并尝试请求，已拒绝请求");
            return null;
        }

        // 允许继续执行原始方法
        return joinPoint.proceed();

    }

    private boolean isIPInList(String ipAddress, String ipRanges) {
        if (ipRanges == null) {
            // 列表为空，不在列表内
            return false;
        }

        String[] split = ipRanges.split(";");


        for (String range : split) {
            range = range.trim();

            if (range.contains("-")) {
                String[] ips = range.split("-");
                String startIp = ips[0].trim();
                String endIp = ips[1].trim();

                // 判断当前请求的IP地址是否在区间内
                if (IPRangeChecker.isIPInRange(ipAddress, startIp, endIp)) {
                    return true;
                }
            } else {
                // 单个IP地址形式
                String singleIp = range;

                // 判断当前请求的IP地址是否与列表中的地址相等
                if (ipAddress.equals(singleIp)) {
                    return true;
                }
            }
        }

        return false;
    }

    private String getClientIP() {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("X-Real-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }
}
