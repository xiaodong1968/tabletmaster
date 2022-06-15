package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.mapper.*;
import com.sxdzsoft.easyresource.service.UserService;
import com.sxdzsoft.easyresource.util.MD5Utils;
import com.sxdzsoft.easyresource.util.NameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName UserServiceImple
 * @Description TODO
 * @Author wujian
 * @Date 2022/4/25 15:16
 * @Version 1.0
 **/
@Service
public class UserServiceImple implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MyDirMapper myDirMapper;
    @Override
    public User queryUserByUsernameEqualsAndIsUseIs(String username, int isUse) {
        return this.userMapper.queryUserByUsernameEqualsAndIsUseIs(username,isUse);
    }

    @Override
    public DataTableModel<User> queryUserForTable(User user, DataTableModel<User> table) {
        Page<User> users=this.userMapper.findAll(new UserSpecification(user), PageRequest.of(table.getStart()/table.getLength(), table.getLength()));
        DataTableModel<User> result=new DataTableModel<User>();
        result.setData(users.getContent());
        result.setRecordsFiltered(Long.valueOf(users.getTotalElements()).intValue());
        result.setRecordsTotal(users.getNumberOfElements());
        return result;
    }

    @Override
    @Transactional
    public int addUser(User user) {
        long n_exist=this.userMapper.countUserByUsernameIsAndIsUseIsNot(user.getUsername(),0);
        if(n_exist>0){
            return HttpResponseRebackCode.SameName;
        }
        long n_exist2=this.userMapper.countUserByRealnameIsAndIsUseIsNot(user.getRealname(),0);
        if(n_exist2>0){
            return HttpResponseRebackCode.SameName;
        }
        Role role=this.roleMapper.getById(user.getRole().getId());
        user.setRole(role);
        user.setPinyinname(NameUtil.getFullSpell(user.getUsername()));
        user.setFirst(NameUtil.getFirstChar(user.getUsername()));
        user.setPassword(MD5Utils.createSaltMD5(user.getPassword()));
        User u=this.userMapper.save(user);
        MyDir myDir=new MyDir();
        myDir.setChild_file_total(0);
        myDir.setChild_total(0);
        myDir.setOwner(u);
        myDir.setIsUse(u.getIsUse());
        myDir.setName(u.getUsername());
        myDir.setSize(0);
        myDir.setRootDir(true);
        myDir.setType(0);
        myDir.setParentId(0);
        this.myDirMapper.save(myDir);
        return HttpResponseRebackCode.Ok;
    }

    @Override
    public User queryUserById(int userId) {
        return this.userMapper.getById(userId);

    }

    @Override
    @Transactional
    public int editUser(User user) {
        User exist=this.userMapper.queryByUsernameIsAndIsUseIsNot(user.getUsername(),0);
        if(exist!=null && exist.getId().intValue()!=user.getId().intValue()){
            return HttpResponseRebackCode.SameName;
        }
        User exist2=this.userMapper.queryByRealnameIsAndIsUseIsNot(user.getRealname(),0);
        if(exist2!=null && exist2.getId().intValue()!=user.getId().intValue()){
            return HttpResponseRebackCode.SameName;
        }
        User currentUser=this.userMapper.getById(user.getId());
        currentUser.setEmail(user.getEmail());
        currentUser.setQq(user.getQq());
        currentUser.setSex(user.getSex());
        currentUser.setTel(user.getTel());
        currentUser.setWechat(user.getWechat());
        currentUser.setUsername(user.getUsername());
        currentUser.setRealname(user.getRealname());
        currentUser.setPinyinname(NameUtil.getFullSpell(user.getUsername()));
        currentUser.setFirst(NameUtil.getFirstChar(user.getUsername()));
        currentUser.setRole(this.roleMapper.getById(user.getRole().getId()));
        currentUser.setPassword(MD5Utils.createSaltMD5(user.getPassword()));
        this.userMapper.save(currentUser);
        return HttpResponseRebackCode.Ok;
    }

    @Override
    @Transactional
    public int changeUser(int userId, int isUse) {
        User u=this.userMapper.getById(userId);
        u.setIsUse(isUse);
        this.userMapper.save(u);
        return HttpResponseRebackCode.Ok;
    }

    @Override
    @Transactional
    public int resetPassword(int userId) {
        User u=this.userMapper.getById(userId);
        u.setPassword(MD5Utils.createSaltMD5("123456"));
        this.userMapper.save(u);
        return HttpResponseRebackCode.Ok;
    }

    @Override
    public List<User> queryUsersByIsUse(int isUse) {
        return this.userMapper.queryByIsUseIsAndIdIsNot(1,1, JpaSort.by("first"));
    }

    @Override
    @Transactional
    public int changeCurrentUserPass(String passwd, User u) {
        User uu=this.userMapper.getById(u.getId());
        uu.setPassword(MD5Utils.createSaltMD5(passwd));
        this.userMapper.save(uu);
        return HttpResponseRebackCode.Ok;
    }
}
