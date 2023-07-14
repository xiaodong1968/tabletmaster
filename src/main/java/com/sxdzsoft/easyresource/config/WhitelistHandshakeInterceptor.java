package com.sxdzsoft.easyresource.config;

import com.sxdzsoft.easyresource.domain.BlackList;
import com.sxdzsoft.easyresource.domain.WhiteList;
import com.sxdzsoft.easyresource.service.BlackListService;
import com.sxdzsoft.easyresource.service.WhiteListService;
import com.sxdzsoft.easyresource.util.IPRangeChecker;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/14 10:47
 * @PackageName:com.sxdzsoft.easyresource.config
 * @ClassName: WhitelistHandshakeInterceptor
 * @Description: TODO
 * @Version 1.0
 */


@Component
public class WhitelistHandshakeInterceptor implements HandshakeInterceptor {


    private CsrfTokenRepository csrfTokenRepository;

    private static final Logger log = LoggerFactory.getLogger("operationLog");

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        WhitelistHandshakeInterceptor.applicationContext = applicationContext;
    }

    private WhiteListService whiteListService;

    private BlackListService blackListService;


    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {

        // 获取请求的URI
        URI uri = request.getURI();

        // 解析URI中的路径
        String path = uri.getPath();
        // 拆分路径
        String[] pathSegments = path.split("/");
        // 获取班级参数
        String clazzName = pathSegments[2];
        // 获取IP参数
        // String ipAddress = pathSegments[3];
        // 获取MAC地址参数
        String macAddress = pathSegments[4];
        // 获取客户端的真实IP地址
        String ipAddress = request.getRemoteAddress().getHostString();

        attributes.put("clazzName", clazzName);
        attributes.put("ipAddress", ipAddress);
        attributes.put("macAddress", macAddress);

        whiteListService = applicationContext.getBean(WhiteListService.class);
        blackListService = applicationContext.getBean(BlackListService.class);

        WhiteList whiteList = whiteListService.queryWhite();
        BlackList blackList = blackListService.queryBlack();

        if (whiteList == null) {
            // 没有设置白名单，拒绝握手
            log.info("【白名单暂未设置】" + ipAddress + " 尝试加入");
            return false;
        }

        // 检查黑名单
        if (blackList!=null && isIPInList(ipAddress, blackList.getIpRanges())) {
            // IP 在黑名单内，拒绝握手
            log.info("IP " + ipAddress + " 在黑名单内并尝试加入，已拒绝连接");
            return false;
        }

        // 检查白名单
        if (!isIPInList(ipAddress, whiteList.getIpRanges())) {
            // IP 不在白名单内，拒绝握手
            log.info("IP " + ipAddress + " 未在白名单内并尝试加入，已拒绝连接");
            return false;
        }

        // IP 在白名单内，允许握手
        return true;


    }

    // 判断IP地址是否在列表（白名单或黑名单）内
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

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {


    }
}
