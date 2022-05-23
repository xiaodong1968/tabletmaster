package com.sxdzsoft.easyresource.security;

import com.sxdzsoft.easyresource.util.MD5Utils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @ClassName MyPasswordEncoder
 * @Description 密码编码、匹配工具
 * @Author wujian
 * @Date 2022/4/25 15:05
 * @Version 1.0
 **/
@Service
public class MyPasswordEncoder implements PasswordEncoder {
    /**
     * @Description 对密码进行编码
     * @Author wujian
     * @Date 15:06 2022/4/25
     * @Params [rawPassword]
     * @Return
     **/
    @Override
    public String encode(CharSequence rawPassword) {
        return MD5Utils.createSaltMD5((String)rawPassword);
    }
    /**
     * @Description 对密码进行校验
     * @Author wujian
     * @Date 15:10 2022/4/25
     * @Params [rawPassword, encodedPassword]
     * @Return
     **/
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
         return MD5Utils.CheckSaltverifyMD5((String)rawPassword, encodedPassword);
    }
}
