package com.sxdzsoft.easyresource.util;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class ServerInfo implements ApplicationListener<WebServerInitializedEvent> {

    private int serverPort;
    private String serverAddress;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        this.serverPort = event.getWebServer().getPort();
        try {
            this.serverAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            // 根据需要适当处理异常
        }
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getServerAddress() {
        return serverAddress;
    }
}

