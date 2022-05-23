package com.sxdzsoft.easyresource.service;

import com.sxdzsoft.easyresource.domain.RoleAuthority;
import com.sxdzsoft.easyresource.mapper.AuMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName AuService
 * @Description 权限服务类
 * @Author wujian
 * @Date 2022/5/13 13:49
 * @Version 1.0
 **/
public interface AuService {
    /**
     * @Description 根据权限父节点获取子节点权限信息
     * @Author wujian
     * @Date 13:50 2022/5/13
     * @Params [parentId, isUse]
     * @Return
     **/
    public List<RoleAuthority> queryAuthorityByParentIdIsAndIsUseIs(Integer parentId, Integer isUse);
}
