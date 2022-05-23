package com.sxdzsoft.easyresource.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName MenuButton
 * @Description 内页功能菜单
 * @Author wujian
 * @Date 2022/5/10 15:20
 * @Version 1.0
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MenuButton {

}
