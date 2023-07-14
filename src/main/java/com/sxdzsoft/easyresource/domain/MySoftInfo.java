package com.sxdzsoft.easyresource.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * @ClassName MySoftInfo
 * @Description 平台信息
 * @Author wujian
 * @Date 2022/5/9 13:54
 * @Version 1.0
 **/
@Service
@ConfigurationProperties(prefix = "mysoft")
public class MySoftInfo {
    @Setter
    @Getter
    private String version;
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String lastUpdate;
    @Setter
    private String modify;
    public String[] getModify(){
        return this.modify.split(";");
    }
    @Override
    public String toString(){
        return this.name;
    }
}
