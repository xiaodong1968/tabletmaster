package com.sxdzsoft.easyresource.util;

import com.sxdzsoft.easyresource.domain.Menu;
import com.sxdzsoft.easyresource.service.MenuService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MenuFilterAop
 * @Description TODO
 * @Author wujian
 * @Date 2022/5/9 15:20
 * @Version 1.0
 **/
@Component
@Aspect
public class MenuFilterAop {
    @Autowired
    private MenuService menuService;
    /**
     * @Description 根据当前用户的权限，过滤没有权限访问的导航菜单
     * @Author wujian
     * @Date 15:20 2022/5/9
     * @Params [point]
     * @Return
     **/
    @Around(value="@annotation(com.sxdzsoft.easyresource.util.MenuFilter)")
    public List<Menu> menuFilter(ProceedingJoinPoint point) throws Throwable {
        //获取当前登录用户的权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authorities=(List<GrantedAuthority>) authentication.getAuthorities();
        //获取系统导航菜单
        List<Menu> menus=(List<Menu>) point.proceed();
        List<Menu> results=new ArrayList<Menu>();
        //筛选符合条件的下级菜单
        for(Menu menu:menus) {
            for(GrantedAuthority up:authorities) {
                if(menu.getAuUrl().equals(up.getAuthority())) {
                    results.add(menu);
                    continue;
                }
            }
        }
        return results;
    }
    /**
     * @Description 根据用户权限，过滤二级栏目内的菜单
     * @Author wujian
     * @Date 15:22 2022/5/10
     * @Params [menuId, model]
     * @Return
     **/
    @AfterReturning(value="@annotation(com.sxdzsoft.easyresource.util.MenuButton) && args(menuId,model)")
    public void MenuButton(Integer menuId, Model model) throws Throwable {
        List<Menu> menus=this.menuService.queryMenuByParentIdAndTypeIsAndIsUseIs(menuId, 2, 1);
        model.addAttribute("menuButton", menus);
    }
}
