package com.sxdzsoft.easyresource.aspect;

import com.sxdzsoft.easyresource.config.WhitelistHandshakeInterceptor;
import com.sxdzsoft.easyresource.domain.WhiteList;
import com.sxdzsoft.easyresource.service.WhiteListService;
import com.sxdzsoft.easyresource.util.IPRangeChecker;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
    private HttpServletRequest request;

    @Around("@annotation(com.sxdzsoft.easyresource.aspect.IPCheck)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String ipAddress = getClientIP();

        WhiteList whiteList = whiteListService.queryWhite();
        // 在这里可以根据获取到的IP地址进行判断和处理
        String allowedStr = whiteList.getAllowedStr();
        String allowedEnd = whiteList.getAllowedEnd();
        boolean allowExecution = IPRangeChecker.isIPInRange(ipAddress, allowedStr, allowedEnd);
//        boolean allowExecution = performIPCheck(ipAddress);

        if (allowExecution) {
            // 允许继续执行原始方法
            return joinPoint.proceed();
        } else {
            // 不执行原始方法，返回自定义的结果或抛出异常
            return null; // 或者抛出异常
        }
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
