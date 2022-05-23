package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.domain.Menu;
import com.sxdzsoft.easyresource.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName MenuHandler
 * @Description 菜单管理
 * @Author wujian
 * @Date 2022/5/9 15:13
 * @Version 1.0
 **/
@Controller
public class MenuHandler {
    @Autowired
    private MenuService menuService;
    /**
     * @Description 根据菜单父节点，获取子菜单
     * @Author wujian
     * @Date 15:34 2022/5/9
     * @Params [parentId]
     * @Return
     **/
    @GetMapping(path = "/queryMenuByParentId")
    @ResponseBody
    public List<Menu> queryMenuByParentId(@RequestParam(name = "parentId", defaultValue = "0") Integer parentId) {
        List<Menu> menus=this.menuService.queryMenuByParentIdAndTypeIsAndIsUseIs(parentId, 1, 1);
        return menus;
    }
}
