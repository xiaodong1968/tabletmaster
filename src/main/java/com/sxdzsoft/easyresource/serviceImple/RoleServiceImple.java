package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.DataTableModel;
import com.sxdzsoft.easyresource.domain.HttpResponseRebackCode;
import com.sxdzsoft.easyresource.domain.Role;
import com.sxdzsoft.easyresource.domain.RoleAuthority;
import com.sxdzsoft.easyresource.mapper.AuMapper;
import com.sxdzsoft.easyresource.mapper.RoleMapper;
import com.sxdzsoft.easyresource.mapper.RoleSpecification;
import com.sxdzsoft.easyresource.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName RoleServiceImple
 * @Description 角色服务实现类
 * @Author wujian
 * @Date 2022/5/10 15:15
 * @Version 1.0
 **/
@Service
public class RoleServiceImple implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AuMapper auMapper;

    @Override
    public DataTableModel<Role> queryUserRoleForTable(Role role, DataTableModel<Role> table) {
        Page<Role> roles=this.roleMapper.findAll(new RoleSpecification(role), PageRequest.of(table.getStart()/table.getLength(), table.getLength()));
        DataTableModel<Role> result=new DataTableModel<Role>();
        result.setData(roles.getContent());
        result.setRecordsFiltered(Long.valueOf(roles.getTotalElements()).intValue());
        result.setRecordsTotal(roles.getNumberOfElements());
        return result;
    }

    @Override
    public Role queryRoleByIdIsAndIsUseIs(int roleId, int isUse) {
        return this.roleMapper.queryRoleByIdIsAndIsUseIs(roleId,isUse);
    }

    @Override
    public long countByNameIsAndIsUseIsNot(String name, int isUse) {
        return this.roleMapper.countByNameIsAndIsUseIsNot(name,isUse);
    }

    @Override
    public Role queryRoleByNameIsAndIsUseIs(String name, int isUse) {
        return this.roleMapper.queryRoleByNameIsAndIsUseIs(name,isUse);
    }

    @Override
    public Role queryRoleByNameIsAndIsUseIsNot(String name, int isUse) {
        return this.roleMapper.queryByNameIsAndIsUseIsNot(name,isUse);
    }

    @Override
    @Transactional
    public int saveRole(Role role, String[] authories) {
        List<RoleAuthority> aus=new ArrayList<RoleAuthority>();
        if(authories!=null) {
            for(int i=0;i<authories.length;i++) {
                RoleAuthority ra= this.auMapper.getById(Integer.valueOf(authories[i]));
                aus.add(ra);
            }
        }
        role.setAuthorities(aus);
        this.roleMapper.save(role);
        return HttpResponseRebackCode.Ok;
    }

    @Override
    @Transactional
    public Role updateRole(Role role, String[] addAu, String[] delAu) {
        Role orgRole=this.roleMapper.getById(role.getId());
        List<RoleAuthority> authories=orgRole.getAuthorities();
        if(addAu!=null) {
            for(int i=0;i<addAu.length;i++) {
                RoleAuthority au= this.auMapper.getById(Integer.valueOf(addAu[i]));
                authories.add(au);
            }
        }
        if(delAu!=null) {
            for(int i=0;i<delAu.length;i++) {
                RoleAuthority au= this.auMapper.getById(Integer.valueOf(delAu[i]));
                authories.remove(au);
            }
        }
        orgRole.setCode(role.getCode());
        orgRole.setName(role.getName());
        orgRole.setAuthorities(authories);
        return this.roleMapper.save(orgRole);
    }

    @Override
    @Transactional
    public Role changeRoleStatue(Role role) {
        Role orgrole=this.roleMapper.getById(role.getId());
        orgrole.setIsUse(role.getIsUse());
        return roleMapper.save(orgrole);
    }

    @Override
    public List<Role> queryByIsUseIs(int isUse) {
        return this.roleMapper.queryByIsUseIsAndIdIsNot(1,1);
    }
}
