package com.sxdzsoft.easyresource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value= "classpath:softInfo.properties")
@EnableConfigurationProperties
public class EasyresourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyresourceApplication.class, args);
    }

}
