package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.RoleAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName AuMapper
 * @Description 权限服务接口
 * @Author wujian
 * @Date 2022/4/20 15:38
 * @Version 1.0
 **/
public interface AuMapper extends JpaRepository<RoleAuthority,Integer> {
    /**
     * @Description 获取所有可用权限
     * @Author wujian
     * @Date 15:40 2022/4/20
     * @Params [isUse]
     * @Return
     **/
    public List<RoleAuthority> queryAllAuthoritesByIsUseEquals(int isUse);
}
