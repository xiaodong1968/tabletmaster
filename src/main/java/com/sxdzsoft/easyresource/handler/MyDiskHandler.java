package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.service.GroupService;
import com.sxdzsoft.easyresource.service.MyDirService;
import com.sxdzsoft.easyresource.service.UserService;
import com.sxdzsoft.easyresource.util.MenuButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName MyDiskHandler
 * @Description 我的网盘处理器
 * @Author wujian
 * @Date 2022/5/19 9:51
 * @Version 1.0
 **/
@Controller
public class MyDiskHandler {
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private MyDirService myDirService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    /**
     * @Description 我的网盘跳转
     * @Author wujian
     * @Date 9:52 2022/5/19
     * @Params [menuId, model]
     * @Return
     **/
    @GetMapping(path="/myDisk")
    @MenuButton
    public String myDisk(Integer menuId, Model model){
        User currentUser=(User)this.httpSession.getAttribute("userinfo");
        MyDir myDir=this.myDirService.queryByOwnerIdIsAndIsUseIsAndTopIs(currentUser.getId(),1,true);
        if(myDir==null){
           myDir = this.myDirService.addUserTopDir(currentUser.getId());
        }
        DirFilesModel dm= this.myDirService.openDir(myDir.getId());
        model.addAttribute("dm", dm);
        model.addAttribute("mydir",myDir);
        model.addAttribute("menuId", menuId);
        return "pages/mydisk/mydisk";
    }
    /**
     * @Description 群组网盘
     * @Author wujian
     * @Date 18:26 2022/6/2
     * @Params [menuId, model]
     * @Return
     **/
    @GetMapping(path="/groupDisk")
    @MenuButton
    public String groupDisk(@RequestParam(defaultValue = "-1") int groupId, Integer menuId, Model model){
        User currentUser=(User)this.httpSession.getAttribute("userinfo");
        List<Group> groups=this.groupService.queryUserGroups(1,currentUser.getId());//获取用户所加入的所有群
        if(groups!=null&&groups.size()>0){
            Group currentGroup;
            if(groupId==-1){
                 currentGroup=groups.get(0);
            }else{
                 currentGroup = this.groupService.queryGroupByIdAndIsUseIs(groupId,1);
            }
            List<User> users=currentGroup.getUsers();//获取第一个群的所有成员
            model.addAttribute("users",users);
            model.addAttribute("groups",groups);
            model.addAttribute("currentGroup",currentGroup.getId());
        }
        model.addAttribute("menuId", menuId);
        return "pages/mydisk/groupDisk";
    }
    /**
     * @Description 跳转只读模式下的个人网盘
     * @Author wujian
     * @Date 18:32 2022/6/2
     * @Params [userId, model]
     * @Return
     **/
    @GetMapping(path="/personDisk")
    public String personDisk(int userId,Model model){
//        User currentUser=(User)this.httpSession.getAttribute("userinfo");
        User currentUser=this.userService.queryUserById(userId);
        MyDir myDir=this.myDirService.queryByOwnerIdIsAndIsUseIsAndTopIs(currentUser.getId(),1,true);
        DirFilesModel dm= this.myDirService.openDir(myDir.getId());
        model.addAttribute("dm", dm);
        model.addAttribute("mydir",myDir);
        model.addAttribute("currentUser",currentUser.getRealname()+"的网盘");
        return "pages/mydisk/personDisk";
    }
    /**
     * @Description 新增个人普通目录
     * @Author wujian
     * @Date 15:26 2022/5/19
     * @Params [name, parentId]
     * @Return
     **/
    @PostMapping(path="/addDir")
    @ResponseBody
    public MyDir addDir(String name,int parentId){
        User currentUser=(User)this.httpSession.getAttribute("userinfo");
        return this.myDirService.addDir(name,parentId,currentUser);
    }
    /**
     * @Description 打开目录
     * @Author wujian
     * @Date 16:10 2022/5/19
     * @Params [parentId]
     * @Return
     **/
    @GetMapping(path="/openDir")
    @ResponseBody
    public DirFilesModel openDir(int parentId){
            return this.myDirService.openDir(parentId);
    }
    /**
     * @Description 重命名目录
     * @Author wujian
     * @Date 14:40 2022/5/20
     * @Params [dirId, name]
     * @Return
     **/
    @PostMapping(path="/restDirName")
    @ResponseBody
    public int resetDirName(int dirId,String name){
        return this.myDirService.resetDirName(dirId,name);
    }
    /**
     * @Description 粘贴目录
     * @Author wujian
     * @Date 16:20 2022/5/20
     * @Params [dirId, parentId]
     * @Return
     **/
    @PostMapping(path="/parseDir")
    @ResponseBody
    public int parseDir(int dirId,int parentId){
        return this.myDirService.parseDir(dirId,parentId);
    }
    /**
     * @Description 删除目录
     * @Author wujian
     * @Date 13:58 2022/5/23
     * @Params [dirId]
     * @Return
     **/
    @PostMapping(path="/delDir")
    @ResponseBody
    public int delDir(int dirId){
        return this.myDirService.delDir(dirId);
    }
    /**
     * @Description 共享目录到群组(选择弹窗)
     * @Author wujian
     * @Date 14:41 2022/5/23
     * @Params [dirId]
     * @Return
     **/
    @GetMapping(path="/shareDirDialog")
    public String shareDirDialog(int dirId,Model model){
        User currentUser=(User) this.httpSession.getAttribute("userinfo");
        List<Group> groups=this.groupService.queryUserGroups(1,currentUser.getId());
        for(Group g:groups){
           int[] ids=g.getDirs().stream().mapToInt(MyDir::getId).toArray();
           for(int id:ids){
               if(id==dirId){
                   g.setChecked(true);
               }
           }
        }
        model.addAttribute("groups",groups);
        model.addAttribute("currentDir",dirId);
        return "pages/mydisk/shareDirDialog";
    }
    /**
     * @Description 共享目录到群组
     * @Author wujian
     * @Date 15:41 2022/5/23
     * @Params [currentDir, groups]
     * @Return
     **/
    @PostMapping(path="/shareDirToGroup")
    @ResponseBody
    public int shareDirToGroup(int currentDir,int[] groups){
        return this.myDirService.shareDirToGroup(currentDir,groups);
    }

}
