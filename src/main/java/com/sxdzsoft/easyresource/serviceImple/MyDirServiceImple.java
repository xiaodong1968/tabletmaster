package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.mapper.GroupMapper;
import com.sxdzsoft.easyresource.mapper.MyDirMapper;
import com.sxdzsoft.easyresource.mapper.MyFileMapper;
import com.sxdzsoft.easyresource.mapper.UserMapper;
import com.sxdzsoft.easyresource.service.MyDirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private MyFileMapper myFileMapper;
    @Override
    public DirFilesModel openDir(int parentId) {
        DirFilesModel dm=new DirFilesModel();
        MyDir currentDir=this.myDirMapper.getById(parentId);
        dm.setParentId(currentDir.getParentId());
        dm.setCurrentName(currentDir.getName());
        List<MyDir> dirs=this.myDirMapper.queryByParentIdIsAndIsUseIs(parentId,1);
        List<MyFile> files=this.myFileMapper.queryByMyDirIdIsAndIsUseIs(parentId,1);
        dm.setDirs(dirs);
        dm.setFiles(files);
        return dm;
    }

    @Override
    public MyDir queryByOwnerIdIsAndIsUseIsAndTopIs(int ownerId, int isUse, boolean isTop) {
        return this.myDirMapper.queryByOwnerIdIsAndIsUseIsAndRootDirIs(ownerId,isUse,isTop);
    }

    @Override
    @Transactional
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
        myDir.setShareToGroup(false);
        return this.myDirMapper.save(myDir);
    }
    @Override
    @Transactional
    public MyDir addDir(String name, int parentId, User owner) {
        MyDir myDir=new MyDir();
        myDir.setRootDir(false);
        myDir.setSize(0);
        myDir.setType(0);
        myDir.setParentId(parentId);
        myDir.setIsUse(1);
        myDir.setShareToGroup(false);
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
    @Transactional
    public int resetDirName(int dirId, String name) {
        MyDir myDir=this.myDirMapper.getById(dirId);
        myDir.setName(name);
        this.myDirMapper.save(myDir);
        return HttpResponseRebackCode.Ok;
    }

    @Override
    @Transactional
    public int parseDir(int dirId, int parentId) {
        MyDir myDir=this.myDirMapper.getById(dirId);
        myDir.setParentId(parentId);
        this.myDirMapper.save(myDir);
        return HttpResponseRebackCode.Ok;
    }

    @Override
    @Transactional
    public int delDir(int dirId) {
        MyDir myDir=this.myDirMapper.getById(dirId);
        myDir.setIsUse(0);
        this.myDirMapper.save(myDir);
        return HttpResponseRebackCode.Ok;
    }

    @Override
    @Transactional
    public int shareDirToGroup(int currentDir, int[] groups) {
        MyDir myDir=this.myDirMapper.getById(currentDir);
        List<Group> newgs=new ArrayList<Group>();
        if(groups!=null){
            for(int i=0;i<groups.length;i++){
                Group g=this.groupMapper.getById(groups[i]);
                newgs.add(g);
            }
            myDir.setGroups(newgs);
        }
        if(newgs!=null&&newgs.size()>0){
            myDir.setShareToGroup(true);
        }else{
            myDir.setShareToGroup(false);
            myDir.setGroups(null);
        }
        this.myDirMapper.save(myDir);
        return HttpResponseRebackCode.Ok;
    }
}
