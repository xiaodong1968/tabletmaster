package com.sxdzsoft.easyresource.service;

import com.sxdzsoft.easyresource.domain.DataTableModel;
import com.sxdzsoft.easyresource.domain.User;

import java.util.List;

/**
 * @ClassName UserService
 * @Description 用户服务类
 * @Author wujian
 * @Date 2022/4/25 15:15
 * @Version 1.0
 **/
public interface UserService {
    /**
     * @Description 根据用户名称获取用户信息
     * @Author wujian
     * @Date 15:16 2022/4/25
     * @Params [username, isUse]
     * @Return
     **/
    public User queryUserByUsernameEqualsAndIsUseIs(String username, int isUse);
    /**
     * @Description 用户分页表格
     * @Author wujian
     * @Date 16:40 2022/5/16
     * @Params [role, table]
     * @Return
     **/
    public DataTableModel<User> queryUserForTable(User user, DataTableModel<User> table);
    /**
     * @Description 新增用户
     * @Author wujian
     * @Date 17:21 2022/5/16
     * @Params [user]
     * @Return
     **/
    public int addUser(User user);
    /**
     * @Description 根据用户ID获取用户
     * @Author wujian
     * @Date 10:52 2022/5/17
     * @Params [userId, isUse]
     * @Return
     **/
    public User queryUserById(int userId);
    /**
     * @Description 修改用户信息
     * @Author wujian
     * @Date 11:01 2022/5/17
     * @Params [user]
     * @Return
     **/
    public int editUser(User user);
    /**
     * @Description 改变用户状态（包含删除）
     * @Author wujian
     * @Date 11:01 2022/5/17
     * @Params [userId, isUse]
     * @Return
     **/
    public int changeUser(int userId,int isUse);

    /**
     * @Description 改变用户状态（包含删除）
     * @Author wujian
     * @Date 11:01 2022/5/17
     * @Params [userId, isUse]
     * @Return
     **/
    public int changeCareUser(int userId,int isCare);
    /**
     * @Description 重置指定用密码，默认为123456
     * @Author wujian
     * @Date 16:29 2022/5/17
     * @Params [userId]
     * @Return
     **/
    public int resetPassword(int userId);
    /**
     * @Description 查询所有正常使用的用户，并排序
     * @Author wujian
     * @Date 16:49 2022/5/17
     * @Params [isUse, sort]
     * @Return
     **/
    public List<User> queryUsersByIsUse(int isUse);
    /**
     * @Description 修改指定用户密码
     * @Author wujian
     * @Date 19:52 2022/6/11
     * @Params [passwd, u]
     * @Return
     **/
    public int changeCurrentUserPass(String passwd,User u);

    /**
     * @Description: 查询再任职心理老师人数
     * @data:[]
     * @return: long
     * @Author: YangXiaoDong
     * @Date: 2023/2/24 12:35
     */
    public long countIsCare();

    /**
     * @Description: 查询任职的心理老师
     * @data:[]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.User>
     * @Author: YangXiaoDong
     * @Date: 2023/2/24 13:13
     */
    public DataTableModel<User> queryCares(Integer Iscare,DataTableModel<User> table);

    /**
     * @Description: 根据用户名称进行模糊查询(只查询心理辅导员)
     * @data:[userName]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.User>
     * @Author: YangXiaoDong
     * @Date: 2023/3/1 15:06
     */
    public List<User> queryNameLike(String userName);

    /**
     * @Description: 根据用户名称进行模糊查询
     * @data:[userName]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.User>
     * @Author: YangXiaoDong
     * @Date: 2023/3/1 15:06
     */
    public List<User> queryAllNameLike(String userName);



}
