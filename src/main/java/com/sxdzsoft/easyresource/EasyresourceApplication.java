package com.sxdzsoft.easyresource;

import com.sxdzsoft.easyresource.handler.WebSocket;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
@EnableConfigurationProperties
public class EasyresourceApplication {

    public static void main(String[] args) {
        //SpringApplication.run(EasyresourceApplication.class, args);

        //修改后-- //解决websocketServer无法注入mapper问题
        SpringApplication springApplication = new SpringApplication(EasyresourceApplication.class);
        ConfigurableApplicationContext configurableApplicationContext = springApplication.run(args);
        WebSocket.setApplicationContext(configurableApplicationContext);
    }

}
