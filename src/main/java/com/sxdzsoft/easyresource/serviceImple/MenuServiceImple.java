package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.Menu;
import com.sxdzsoft.easyresource.mapper.MenuMapper;
import com.sxdzsoft.easyresource.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName MenuServiceImple
 * @Description TODO
 * @Author wujian
 * @Date 2022/4/20 16:44
 * @Version 1.0
 **/
@Service
public class MenuServiceImple implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    /**
     * @Description 根据菜单父节点，菜单类型，启用状态获取菜单信息
     * @Author wujian
     * @Date 16:46 2022/4/20
     * @Params [MenuId, type, isUse]
     * @Return
     **/
    @Override
    public List<Menu> queryMenuByParentIdAndTypeIsAndIsUseIs(Integer MenuId, Integer type, Integer isUse) {
        return this.menuMapper.queryMenuByParentIdAndTypeIsAndIsUseIs(MenuId,type,isUse);
    }
}
