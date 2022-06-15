package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.mapper.GroupMapper;
import com.sxdzsoft.easyresource.mapper.GroupSpecification;
import com.sxdzsoft.easyresource.mapper.RoleSpecification;
import com.sxdzsoft.easyresource.mapper.UserMapper;
import com.sxdzsoft.easyresource.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.ListUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @ClassName GroupServiceImple
 * @Description TODO
 * @Author wujian
 * @Date 2022/5/13 15:48
 * @Version 1.0
 **/
@Service
public class GroupServiceImple implements GroupService {
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public DataTableModel<Group> queryGroupForTable(Group group, DataTableModel<Group> table) {
        Page<Group> groups=this.groupMapper.findAll(new GroupSpecification(group), PageRequest.of(table.getStart()/table.getLength(), table.getLength()));
        DataTableModel<Group> result=new DataTableModel<Group>();
        result.setData(groups.getContent());
        result.setRecordsFiltered(Long.valueOf(groups.getTotalElements()).intValue());
        result.setRecordsTotal(groups.getNumberOfElements());
        return result;
    }
    @Override
    public long countByNameIsAndIsUseNot(String name, int isUse) {
        return this.groupMapper.countByNameIsAndIsUseNot(name,isUse);
    }

    @Override
    @Transactional
    public int addGroup(Group group, int[] admins, int[] users, User currentUser) {
        long r= this.groupMapper.countByNameIsAndIsUseNot(group.getName(),0);
        if(r>0){
            return HttpResponseRebackCode.SameName;
        }
        //只允许存在一个超级群组
        if(group.getType()==0){
           int exist= this.groupMapper.queryByTypeIsAndIsUseIs(0,1).size();
           if(exist>0){
               return HttpResponseRebackCode.Fail;
           }
        }
        group.setCreateTime(new Date());
        group.setCreater(currentUser);
        List<User> adminsTemp=new ArrayList<User>();
        List<User> usersTemp=new ArrayList<User>();
        if(admins!=null){
            for(int i=0;i<admins.length;i++){
                User u=this.userMapper.getById(admins[i]);
                if(u!=null){
                    adminsTemp.add(u);
                }
            }
        }
        if(adminsTemp==null||adminsTemp.size()==0){
            adminsTemp.add(currentUser);
        }
        if(users!=null){
            for(int j=0;j<users.length;j++){
                User u=this.userMapper.getById(users[j]);
                if(u!=null){
                    usersTemp.add(u);
                }
            }
        }
        if(usersTemp==null||usersTemp.size()==0){
            usersTemp.add(currentUser);
        }
        group.setAdmins(adminsTemp);
        group.setUsers(usersTemp);
        this.groupMapper.save(group);
        return HttpResponseRebackCode.Ok;
    }

    @Override
    public List<User> queryGroupUsers(int groupId) {
        Group group=this.groupMapper.getById(groupId);
        List<User> res=new ArrayList<User>();
        if(group!=null){
            List<User> admins=group.getAdmins();
            List<User> users=group.getUsers();
            for(User u:users){
                if(admins.contains(u)){
                    u.setAdmin(true);
                }
                res.add(u);
            }
            Collections.sort(res);
        }
        return res;
    }

    @Override
    public Group queryGroupByIdAndIsUseIs(int groupId, int isUse) {
        return this.groupMapper.queryByIdIsAndIsUseIs(groupId,isUse);
    }
    @Override
    @Transactional
    public int editGroup(Group group, int[] admins, int[] users, User currentUser) {
        Group exist=this.groupMapper.queryByNameIsAndIsUseIsNot(group.getName(),0);
        if(exist!=null&&exist.getId().intValue()!=group.getId().intValue()){
            return HttpResponseRebackCode.SameName;
        }
        Group g=this.groupMapper.getById(group.getId());
        g.setName(group.getName());
        g.setIsUse(group.getIsUse());
        g.setInfo(group.getInfo());
        List<User> adminsTemp=new ArrayList<User>();
        List<User> usersTemp=new ArrayList<User>();
        if(admins!=null){
            for(int i=0;i<admins.length;i++){
                User u=this.userMapper.getById(admins[i]);
                if(u!=null){
                    adminsTemp.add(u);
                }
            }
        }
        if(users!=null){
            for(int j=0;j<users.length;j++){
                User u=this.userMapper.getById(users[j]);
                if(u!=null){
                    usersTemp.add(u);
                }
            }
        }
        if(adminsTemp.size()>0&&usersTemp.size()>0){
            g.setAdmins(adminsTemp);
            g.setUsers(usersTemp);
        }
        this.groupMapper.save(g);
        return HttpResponseRebackCode.Ok;
    }

    @Override
    @Transactional
    public int changeGroup(int groupId, int isUse) {
        Group g=this.groupMapper.getById(groupId);
        g.setIsUse(isUse);
        this.groupMapper.save(g);
        return HttpResponseRebackCode.Ok;
    }

    @Override
    @Transactional
    public List<Group> queryUserGroups(int isUse, int userId) {
        User u= this.userMapper.getById(userId);
        return    u.getGroups();

    }

    @Override
    public List<Group> queryGroups(int isUse) {
        return this.groupMapper.queryByIsUseIs(isUse);
    }

    @Override
    public List<Group> queryByTypeIsAndIsUseIs(int type, int isUse) {
        return this.groupMapper.queryByTypeIsAndIsUseIs(type,isUse);
    }

    @Override
    public List<Group> queryByParentGroupIdIsAndIsUseIs(int parentGroupId, int isUse) {
        return this.groupMapper.queryByParentGroupIdIsAndIsUseIs(parentGroupId,isUse);
    }

    @Override
    public Group queryGroupByIdAndIsUseIsNot(int groupId, int isUse) {
        return this.groupMapper.queryByIdIsAndIsUseIsNot(groupId,isUse);
    }
}
