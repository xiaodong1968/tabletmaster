package com.sxdzsoft.easyresource.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName MenuFilter
 * @Description TODO
 * @Author wujian
 * @Date 2022/5/9 15:18
 * @Version 1.0
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MenuFilter {
}
