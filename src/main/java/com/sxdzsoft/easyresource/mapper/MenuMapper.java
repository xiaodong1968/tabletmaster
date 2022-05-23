package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.Menu;
import com.sxdzsoft.easyresource.domain.RoleAuthority;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @ClassName MenuMapper
 * @Description 系统菜单服务数据接口
 * @Author wujian
 * @Date 2022/4/20 16:45
 * @Version 1.0
 **/
public interface MenuMapper  extends JpaRepository<Menu,Integer> {
    /**
     * @Description 根据菜单父节点，菜单类型，启用状态获取菜单信息
     * @Author wujian
     * @Date 16:36 2022/4/20
     * @Params [MenuId, type, isUse]
     * @Return
     **/
    public List<Menu> queryMenuByParentIdAndTypeIsAndIsUseIs(Integer MenuId, Integer type, Integer isUse, Sort sort);
    /**
     * @Description 根据请求路径获取请求所需权限（适用于菜单）
     * @Author wujian
     * @Date 16:14 2022/5/10
     * @Params [href]
     * @Return
     **/
    @Query("select m.auUrl from Menu as m where m.href=:href and m.isUse=1")
    public String queryAuUrlByHref(@Param("href") String href);
}
