package com.sxdzsoft.easyresource.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import java.io.IOException;

/**
 * @ClassName MySecurityFilter
 * @Description Security过滤器
 * @Author wujian
 * @Date 2022/4/20 15:11
 * @Version 1.0
 **/
@Service
public class MySecurityFilter extends AbstractSecurityInterceptor implements Filter {
    @Autowired
    private MyFilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadataSource;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //do nothing
    }

    /**
     * @Description 执行过滤
     * @Author wujian
     * @Date 15:15 2022/4/20
     * @Params [servletRequest, servletResponse, filterChain]
     * @Return
     **/
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /**
         * 1.为当前请求建立过滤链节点
         * 2.servletRequest,servletResponse为当前请求的请求与响应对象
         * 3、chain为过滤节点所属过滤链
         */
        FilterInvocation fi=new FilterInvocation(servletRequest,servletResponse,filterChain);
        /**
         * 1、执行过滤链节点
         * 2、调用父类方法：1）获取当前请求所需的访问权限
         *               2）通过投票器决定当前用户是否有权限访问请求
         */
        InterceptorStatusToken token=super.beforeInvocation(fi);
        /**
         * 如果请求通过，执行过滤器链上的其他节点
         */
        try{
            fi.getChain().doFilter(fi.getRequest(),fi.getResponse());
        }finally {
            super.afterInvocation(token,null);
        }
    }

    @Override
    public void destroy() {
        //donothing
    }

    @Override
    public Class<?> getSecureObjectClass() {

        return FilterInvocation.class;
    }
    /**
     * @Description TODO
     * @Author 获取访问请求所需的权限
     * @Date 15:48 2022/4/20
     * @Params []
     * @Return
     **/
    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.myFilterInvocationSecurityMetadataSource;
    }
    /**
     * @Description TODO
     * @Author 设置投票器
     * @Date 16:00 2022/4/20
     * @Params [accessDecisionManager]
     * @Return
     **/
    @Autowired
    public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
        // TODO Auto-generated method stub
        super.setAccessDecisionManager(accessDecisionManager);
    }

}
