package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
      * @Description 根据用户真实名统计未被删除的用户数量
      * @Author wujian
      * @Date 19:15 2022/6/11
      * @Params [realName, isUse]
      * @Return
      **/
    public long countUserByRealnameIsAndIsUseIsNot(String realName,int isUse);
     /**
      * @Description 根据用户名查询未被删除的用户
      * @Author wujian
      * @Date 11:08 2022/5/17
      * @Params [username, isUse]
      * @Return
      **/
     public User queryByUsernameIsAndIsUseIsNot(String username,int isUse);
     /**
      * @Description 根据用户真实名查询未被删除的用户
      * @Author wujian
      * @Date 19:17 2022/6/11
      * @Params [realName, isUse]
      * @Return
      **/
    public User queryByRealnameIsAndIsUseIsNot(String realName,int isUse);
     /**
      * @Description 获取所有正常使用的用户(不包含超级管理员），并排序
      * @Author wujian
      * @Date 16:48 2022/5/17
      * @Params [isUse, sort]
      * @Return
      **/
     public List<User> queryByIsUseIsAndIdIsNot(int isUse,int id, Sort sort);

    /**
     * @Description: 根据用户名称模糊查询(只查询心理辅导员)
     * @data:[username]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.User>
     * @Author: YangXiaoDong
     * @Date: 2023/2/20 10:19
     */

    @Query("SELECT u FROM User u WHERE u.username LIKE CONCAT('%',:username,'%') and u.isCare = 1")
    public List<User> queryNameLike(@Param("username") String username);

    /**
     * @Description: 根据用户名称模糊查询
     * @data:[username]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.User>
     * @Author: YangXiaoDong
     * @Date: 2023/2/20 10:19
     */
    @Query("SELECT u FROM User u WHERE u.username LIKE CONCAT('%',:username,'%')")
    public List<User> queryALlNameLike(@Param("username") String username);


    /**
     * @Description: 查询心理老师人数
     * @data:[]
     * @return: long
     * @Author: YangXiaoDong
     * @Date: 2023/2/24 12:35
     */
    @Query(value = "SELECT COUNT(1) FROM t_user_db WHERE is_care = 1",nativeQuery = true)
    public int countIsCare();


    /**
     * @Description: 查询再任职的心理老师
     * @data:[]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.User>
     * @Author: YangXiaoDong
     * @Date: 2023/2/24 13:13
     */
    public List<User> queryByIsCare(Integer isCare);

    /**
     * @Description: 查询再任职的心理老师(分页)
     * @data:[]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.User>
     * @Author: YangXiaoDong
     * @Date: 2023/2/24 13:13
     */
    public Page<User> getUserByIsCare(Integer isCare, Pageable pageable);

}
