package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.Menu;
import com.sxdzsoft.easyresource.domain.RoleAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

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
    public List<Menu> queryMenuByParentIdAndTypeIsAndIsUseIs(Integer MenuId, Integer type, Integer isUse);
}
