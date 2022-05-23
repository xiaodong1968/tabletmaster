package com.sxdzsoft.easyresource.security;

import com.sxdzsoft.easyresource.domain.Role;
import com.sxdzsoft.easyresource.domain.RoleAuthority;
import com.sxdzsoft.easyresource.domain.User;
import com.sxdzsoft.easyresource.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MyUserDetailsService
 * @Description Security登录验证时，获取用户信息
 * @Author wujian
 * @Date 2022/4/25 15:11
 * @Version 1.0
 **/
@Service
public class MyUserDetailsService  implements UserDetailsService {
    @Autowired
    private UserService userService;
    /**
     *
     * Author:WuJian
     * Description:根据用户输入用户名查询用户的信息并获取权限信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     * MyUserDetailsService.java
     * LastModify:2020年2月2日
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =this.userService.queryUserByUsernameEqualsAndIsUseIs(username,1);
        if(user==null) {
            throw new UsernameNotFoundException(username);
        }
        if(user!=null) {
            Role role = user.getRole();
            if(role.getIsUse()!=1) {
                user.setAuthorities(null);
                return user;
            }
            List<GrantedAuthority> aus = new ArrayList<GrantedAuthority>();
            for (RoleAuthority au : role.getAuthorities()) {
                aus.add(new SimpleGrantedAuthority(au.getCode()));
            }
            user.setAuthorities(aus);
        }
        return user;
    }
}
