package com.sxdzsoft.easyresource.security;


import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName MyAccessDecision
 * @Description 投票器，用于决定当前的用户是否可以访问请求
 * @Author wujian
 * @Date 2022/4/20 15:53
 * @Version 1.0
 **/
@Service("myAccessDecision")
public class MyAccessDecision implements AccessDecisionManager {
    /**
     * @Description 投票决定当前用户是否可以访问请求
     * @Author wujian
     * @Date 15:54 2022/4/20
     * @Params [
     *          authentication:当前登录用户,
     *          object,
     *          configAttributes]
     * @Return
     **/
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        //如果当前用户未登录直接拒绝访问
        if(authentication==null){
            throw new AccessDeniedException("403");
        }
        if(null==configAttributes||configAttributes.size()<=0)
        {
            throw new AccessDeniedException("403");
        }
        //获取当前用户的权限
        List<String> upermissions=authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        //获取访问当前请求所需的权限
        List<String> permissions=configAttributes.stream().map(ConfigAttribute::getAttribute).collect(Collectors.toList());
        //判断当前用户是否具有访问请求所需的权限
        for(String per:upermissions) {
            if(permissions.contains(per))
                return ;
        }
        throw new AccessDeniedException("403");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
