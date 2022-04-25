package com.sxdzsoft.easyresource.security;

import com.sxdzsoft.easyresource.domain.RoleAuthority;
import com.sxdzsoft.easyresource.mapper.AuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName MyFilterInvocationSecurityMetadataSource
 * @Description 访问请求所需的权限
 * @Author wujian
 * @Date 2022/4/20 15:23
 * @Version 1.0
 **/
@Service
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    public List<String> urls=new ArrayList<String>();
    /**
     * @Description 获取访问请求需的权限
     * @Author wujian
     * @Date 15:26 2022/4/20
     * @Params [object]
     * @Return
     **/
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi=(FilterInvocation)object;//获取当前请求的过滤器链节点
        String requestUrl=fi.getRequestUrl();//获取请求的url
        List<ConfigAttribute> attributes=new ArrayList<ConfigAttribute>();
        int paramIndex=requestUrl.indexOf("?");//为了判断请求的URL中是否有请求参数
        if(paramIndex!=-1){
            requestUrl.substring(0,paramIndex);
        }
        requestUrl=requestUrl.substring(1);//在权限表中，权限的形式例：addUser,而获取到的url是/addUser，需要去掉/。
        /**
         * 判断当前的请求是否包含在系统的权限表中。
         * 在本系统的设计中，所有需要拦截的请求及对应的访问权限会保存在权限表中，如果某个请求不包含在该权限表，则表明该请求只需登录后即可访问，
         * 不需要拥有特别的权限。
         */
        if(!this.urls.contains(requestUrl)){
            return null;
        }
        SecurityConfig sy=new SecurityConfig(requestUrl);
        attributes.add(sy);
        return attributes;
    }
    /**
     * @Description TODO
     * @Author 获取系统的全部权限
     * @Date 15:41 2022/4/20
     * @Params [auService]
     * @Return
     **/
    @Autowired
    public  void getAllAuthories(AuMapper auService){
        List<String> urls=auService.queryAllAuthoritesByIsUseEquals(1).stream().map(RoleAuthority::getCode).collect(Collectors.toList());
        this.urls=urls;
    }
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
