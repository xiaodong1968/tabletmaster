package com.sxdzsoft.easyresource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/4 10:54
 * @PackageName:com.eastwind.config
 * @ClassName: WebSocketConfig
 * @Description: TODO
 * @Version 1.0
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

}
