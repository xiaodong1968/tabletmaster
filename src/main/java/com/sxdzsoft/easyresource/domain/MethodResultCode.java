package com.sxdzsoft.easyresource.domain;

/**
 * 方法完成结果码
 */
public abstract class MethodResultCode {
        public static final int  SUCCESS=1;//执行成功
        public static final int  FAIL=0;//执行失败
        public static final int  SAME_NAME=2;//名称重复
        public static final int  SAME_HREF=3;//请求路径重复
}
