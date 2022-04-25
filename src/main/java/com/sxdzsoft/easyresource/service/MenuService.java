package com.sxdzsoft.easyresource.service;

import com.sxdzsoft.easyresource.domain.Menu;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName MenuService
 * @Description 系统菜单服务接口
 * @Author wujian
 * @Date 2022/4/20 16:27
 * @Version 1.0
 **/
public interface MenuService {
    /**
     * @Description 根据菜单父节点，菜单类型，启用状态获取菜单信息
     * @Author wujian
     * @Date 16:36 2022/4/20
     * @Params [MenuId, type, isUse]
     * @Return
     **/
    public List<Menu> queryMenuByParentIdAndTypeIsAndIsUseIs(Integer MenuId, Integer type, Integer isUse);
}
