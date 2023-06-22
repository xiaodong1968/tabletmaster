package com.sxdzsoft.easyresource.config;

import com.sxdzsoft.easyresource.handler.WebSocket;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/4 10:54
 * @PackageName:com.eastwind.config
 * @ClassName: WebSocketConfig
 * @Description: TODO
 * @Version 1.0
 */
@Component
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer  {




    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocket(), "/websocket/{clazzName}/{ipAddress}/{macAddress}")
                .addInterceptors(new WhitelistHandshakeInterceptor())
                .setAllowedOrigins("*");
    }


    @Bean
    public WebSocket myWebSocketHandler() {
        return new WebSocket();
    }

    @Bean
    public DefaultHandshakeHandler defaultHandshakeHandler() {
        return new DefaultHandshakeHandler();
    }


}
