package com.sxdzsoft.easyresource.temp;

import com.sxdzsoft.easyresource.domain.Group;
import com.sxdzsoft.easyresource.domain.MyDir;
import com.sxdzsoft.easyresource.domain.Role;
import com.sxdzsoft.easyresource.domain.User;
import com.sxdzsoft.easyresource.mapper.GroupMapper;
import com.sxdzsoft.easyresource.mapper.MyDirMapper;
import com.sxdzsoft.easyresource.mapper.RoleMapper;
import com.sxdzsoft.easyresource.mapper.UserMapper;
import com.sxdzsoft.easyresource.service.UserService;
import com.sxdzsoft.easyresource.util.MD5Utils;
import com.sxdzsoft.easyresource.util.NameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName TempUserService
 * @Description TODO
 * @Author wujian
 * @Date 2022/6/9 14:04
 * @Version 1.0
 **/
@Service
public class TempUserService {
    @Autowired
    private TempUserMapper tempUserMapper;
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MyDirMapper myDirMapper;
    @Autowired
    private UserMapper userMapper;
    @Transactional
    public void loadUser(){
        List<TempUser> users= this.tempUserMapper.findAll();
        Group root= this.groupMapper.getById(1);
        for(TempUser u:users){
            User user=new User();
            user.setIsUse(1);
            user.setPassword("123456");
            user.setRealname(u.getName());
            user.setUsername(u.getName());
            if(u.getName().equalsIgnoreCase("刘隼")){
                Role role= this.roleMapper.getById(2);
                user.setRole(role);
            }
            else{
                Role role= this.roleMapper.getById(7);
                user.setRole(role);
            }
            user.setPinyinname(NameUtil.getFullSpell(user.getUsername()));
            user.setFirst(NameUtil.getFirstChar(user.getUsername()));
            user.setPassword(MD5Utils.createSaltMD5(user.getPassword()));
            user=this.userMapper.save(user);
            MyDir myDir=new MyDir();
            myDir.setChild_file_total(0);
            myDir.setChild_total(0);
            myDir.setOwner(user);
            myDir.setIsUse(user.getIsUse());
            myDir.setName(user.getUsername());
            myDir.setSize(0);
            myDir.setRootDir(true);
            myDir.setType(0);
            myDir.setParentId(0);
            this.myDirMapper.save(myDir);
        }
    }
    @Transactional
    public void addGroupUser(){
        List<Group> groups=this.groupMapper.findAll();
        Map<String,ArrayList<User>> maps=new HashMap<String,ArrayList<User>>();
        for(Group g:groups){
            maps.put(g.getName(),new ArrayList<User>());
        }
        List<TempUser> tempUsers= this.tempUserMapper.findAll();
        List<User> users= this.userMapper.findAll();
        for(TempUser u:tempUsers){
           if(maps.containsKey(u.getGroup())){
              User uu= this.userMapper.queryByUsernameIsAndIsUseIsNot(u.getName(),0);
              maps.get(u.getGroup()).add(uu);
           }
        }
        Set<String> keysIts=maps.keySet();
        Iterator<String> keys=keysIts.iterator();
        while(keys.hasNext()){
            String name=keys.next();
            ArrayList<User> uuus=maps.get(name);
            Group g=this.groupMapper.queryByNameIsAndIsUseIsNot(name,0);
            g.setUsers(uuus);
            this.groupMapper.save(g);
        }
        Group root=this.groupMapper.getById(1);
        List<User> rootUsers=new ArrayList<User>();
        for(User u:users){
            rootUsers.add(u);
        }
        root.setUsers(rootUsers);
        this.groupMapper.save(root);
    }
}
