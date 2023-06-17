package com.sxdzsoft.easyresource.config;

import com.sxdzsoft.easyresource.domain.WhiteList;
import com.sxdzsoft.easyresource.service.WhiteListService;
import com.sxdzsoft.easyresource.util.IPRangeChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.URI;
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
@Slf4j
public class WhitelistHandshakeInterceptor implements HandshakeInterceptor  {

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        WhitelistHandshakeInterceptor.applicationContext = applicationContext;
    }

    private WhiteListService whiteListService;


    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes)  {

        // 获取请求的URI
        URI uri = request.getURI();

        // 解析URI中的路径
        String path = uri.getPath();
        // 拆分路径
        String[] pathSegments = path.split("/");
        // 获取班级参数
        String clazzName = pathSegments[2];
        // 获取IP参数
        String ipAddress = pathSegments[3];
        // 获取MAC地址参数
        String macAddress = pathSegments[4];

        attributes.put("clazzName", clazzName);
        attributes.put("ipAddress", ipAddress);
        attributes.put("macAddress", macAddress);

        whiteListService = applicationContext.getBean(WhiteListService.class);
        WhiteList whiteList = whiteListService.queryWhite();

        if (whiteList == null) {
            // 没有设置白名单，拒绝握手
            log.info("【白名单暂未设置】" +ipAddress+" 尝试加入");
            return false;
        }

        String allowedStr = whiteList.getAllowedStr();
        String allowedEnd = whiteList.getAllowedEnd();
        boolean ipInRange = IPRangeChecker.isIPInRange(ipAddress, allowedStr, allowedEnd);

        if (!ipInRange) {
            // IP 不在白名单内，拒绝握手
            log.info("IP "+ipAddress+" 未在白名单内并尝试加入,已拒绝连接");
            return false;
        }

        // IP 在白名单内，允许握手
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // 不需要执行任何操作
    }
}
