package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.mapper.MyDirMapper;
import com.sxdzsoft.easyresource.mapper.UserMapper;
import com.sxdzsoft.easyresource.service.MyDirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName MyDirServiceImple
 * @Description TODO
 * @Author wujian
 * @Date 2022/5/19 14:20
 * @Version 1.0
 **/
@Service
public class MyDirServiceImple implements MyDirService {
    @Autowired
    private MyDirMapper myDirMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public DirFilesModel openDir(int parentId) {

        DirFilesModel dm=new DirFilesModel();
        MyDir currentDir=this.myDirMapper.getById(parentId);
        dm.setParentId(currentDir.getParentId());
        dm.setCurrentName(currentDir.getName());
        List<MyDir> dirs=this.myDirMapper.queryByParentIdIsAndIsUseIs(parentId,1);
        dm.setDirs(dirs);
        return dm;
    }

    @Override
    public MyDir queryByOwnerIdIsAndIsUseIsAndTopIs(int ownerId, int isUse, boolean isTop) {
        return this.myDirMapper.queryByOwnerIdIsAndIsUseIsAndRootDirIs(ownerId,isUse,isTop);
    }

    @Override
    public MyDir addUserTopDir(int userId) {
        User u=this.userMapper.getById(userId);
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
        return this.myDirMapper.save(myDir);
    }
    /**
     * @Description 创建普通个人目录
     * @Author wujian
     * @Date 15:22 2022/5/19
     * @Params [name, parentId, owner]
     * @Return
     **/
    @Override
    public MyDir addDir(String name, int parentId, User owner) {
        MyDir myDir=new MyDir();
        myDir.setRootDir(false);
        myDir.setSize(0);
        myDir.setType(0);
        myDir.setParentId(parentId);
        myDir.setIsUse(1);
        if(this.myDirMapper.countByNameIsAndIsUseIsAndParentIdIs(name,1,parentId)!=0){
            myDir.setName(name+"(有重名)");
        }else {
            myDir.setName(name);
        }
        myDir.setChild_total(0);
        myDir.setChild_file_total(0);
        myDir.setOwner(this.userMapper.getById(owner.getId()));
        myDir=this.myDirMapper.save(myDir);
        MyDir parent=this.myDirMapper.getById(parentId);
        parent.setChild_total(parent.getChild_total()+1);
        this.myDirMapper.save(parent);
        return myDir;
    }

    @Override
    public int resetDirName(int dirId, String name) {
        MyDir myDir=this.myDirMapper.getById(dirId);
        myDir.setName(name);
        this.myDirMapper.save(myDir);
        return HttpResponseRebackCode.Ok;
    }

    @Override
    public int parseDir(int dirId, int parentId) {
        MyDir myDir=this.myDirMapper.getById(dirId);
        myDir.setParentId(parentId);
        this.myDirMapper.save(myDir);
        return HttpResponseRebackCode.Ok;
    }
}
