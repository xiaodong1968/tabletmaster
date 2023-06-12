package com.sxdzsoft.easyresource.config;

import com.sxdzsoft.easyresource.util.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

/**
 * @ClassName WebConfig
 * @Description TODO
 * @Author wujian
 * @Date 2022/4/25 15:36
 * @Version 1.0
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean(){
        FilterRegistrationBean<Filter> fr=new FilterRegistrationBean<Filter>();
        XssFilter f=new XssFilter();
        fr.setFilter(f);
        return fr;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/tablemasterupload/**").addResourceLocations("file:D:/tablemasterupload/");
    }

}
