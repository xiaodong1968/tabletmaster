package com.sxdzsoft.easyresource.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @Author YangXiaoDong
 * @Date 2023/6/22 11:12
 * @PackageName:com.sxdzsoft.easyresource.security
 * @ClassName: IPCheck
 * @Description: TODO
 * @Version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IPCheck {
    // 可添加自定义的注解属性
}
