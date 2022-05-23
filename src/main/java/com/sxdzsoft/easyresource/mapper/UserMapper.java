package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @ClassName UserMapper
 * @Description 用户数据服务接口
 * @Author wujian
 * @Date 2022/4/25 15:14
 * @Version 1.0
 **/
public interface UserMapper  extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    /**
     * @Description 根据用户名查找用户信息
     * @Author wujian
     * @Date 15:15 2022/4/25
     * @Params [username, isUse]
     * @Return
     **/
    public User queryUserByUsernameEqualsAndIsUseIs(String username,int isUse);
    /**
     * @Description 根据用户名统计未被删除的用户数量
     * @Author wujian
     * @Date 17:25 2022/5/16
     * @Params
     * @Return
     **/
     public long countUserByUsernameIsAndIsUseIsNot(String username,int isUse);
     /**
      * @Description 根据用户名查询未被删除的用户
      * @Author wujian
      * @Date 11:08 2022/5/17
      * @Params [username, isUse]
      * @Return
      **/
     public User queryByUsernameIsAndIsUseIsNot(String username,int isUse);
     /**
      * @Description 获取所有正常使用的用户(不包含超级管理员），并排序
      * @Author wujian
      * @Date 16:48 2022/5/17
      * @Params [isUse, sort]
      * @Return
      **/
     public List<User> queryByIsUseIsAndIdIsNot(int isUse,int id, Sort sort);
}
