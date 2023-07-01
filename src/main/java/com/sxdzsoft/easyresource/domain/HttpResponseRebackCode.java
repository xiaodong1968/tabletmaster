package com.sxdzsoft.easyresource.domain;

/**
 * @ClassName HttpResponseCode
 * @Description 请求返回码
 * @Author wujian
 * @Date 2022/5/13 14:26
 * @Version 1.0
 **/
public abstract class HttpResponseRebackCode {
    public static final int InValidate=-2;//非法访问
    public static final int Ok=1;//请求成功
    public static final int Fail=0;//请求失败
    public static final int SameName=-1;//同名
    public static final int HasDo=2;//请求已经完成
    public static final int InValidateOrder=3;//非法请求顺序
    public static final int WaitAu=4;//等待审核
    public static final int NotFound=-3;//无查询结果
    public static final int BadNet=-4;//无法连接
    public static final int sameIp=-5;//IP
    public static final int SomeHasDo=-6;//请求部分完成
    public static final int InvalidateDate=-7;//非法时间请求
    public static final int codeReuse=-8;//角色代码重复
    public static final int overMax=-9;
    public static final int lessMin=-10;

}
