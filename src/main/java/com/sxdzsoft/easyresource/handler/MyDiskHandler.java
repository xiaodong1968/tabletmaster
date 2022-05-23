package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.domain.DataTableModel;
import com.sxdzsoft.easyresource.domain.DirFilesModel;
import com.sxdzsoft.easyresource.domain.MyDir;
import com.sxdzsoft.easyresource.domain.User;
import com.sxdzsoft.easyresource.service.MyDirService;
import com.sxdzsoft.easyresource.util.MenuButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

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
}
