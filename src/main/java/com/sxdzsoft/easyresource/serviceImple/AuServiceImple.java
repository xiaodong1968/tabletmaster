package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.RoleAuthority;
import com.sxdzsoft.easyresource.mapper.AuMapper;
import com.sxdzsoft.easyresource.service.AuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName AuServiceImple
 * @Description 权限服务类
 * @Author wujian
 * @Date 2022/5/13 13:51
 * @Version 1.0
 **/
@Service
public class AuServiceImple implements AuService {
    @Autowired
    private AuMapper auMapper;
    /**
     * @Description 根据父节点权限获取子节点权限信息
     * @Author wujian
     * @Date 13:51 2022/5/13
     * @Params [parentId, isUse]
     * @Return
     **/
    @Override
    public List<RoleAuthority> queryAuthorityByParentIdIsAndIsUseIs(Integer parentId, Integer isUse) {
        return this.auMapper.queryAuthorityByParentIdIsAndIsUseIs(parentId,isUse);
    }
}
