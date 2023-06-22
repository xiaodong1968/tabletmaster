package com.sxdzsoft.easyresource.security;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/21 9:09
 * @PackageName:com.sxdzsoft.easyresource.security
 * @ClassName: TokenAuthenticationFilter
 * @Description: TODO
 * @Version 1.0
 */


public class TokenAuthenticationFilter extends OncePerRequestFilter {



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            // 在这里编写特殊请求的放行逻辑
            String specialParameter = request.getParameter("specialParam");
            if (specialParameter != null && specialParameter.equals("your_special_value")) {
                // 特殊请求的放行操作
                filterChain.doFilter(request, response);
                return;
            } else {
                // 特殊请求的放行条件不满足，返回错误响应
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }

        // 继续执行过滤器链


}

