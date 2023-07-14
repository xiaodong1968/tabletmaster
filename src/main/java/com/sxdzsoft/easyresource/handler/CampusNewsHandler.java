package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.form.*;
import com.sxdzsoft.easyresource.aspect.IPCheck;
import com.sxdzsoft.easyresource.service.*;
import com.sxdzsoft.easyresource.util.MenuButton;
import com.sxdzsoft.easyresource.util.ServerInfo;
import com.sxdzsoft.easyresource.util.TimeFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/15 11:29
 * @PackageName:com.sxdzsoft.easyresource.handler
 * @ClassName: CampusNewsHandler
 * @Description: TODO
 * @Version 1.0
 */
@Controller
public class CampusNewsHandler {

    @Autowired
    private CampusNewService campusNewService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MyFileService myFileService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    public ServerInfo serverInfo;

    @Autowired
    private WebSocket webSocket;

    @Autowired
    private CampusNewsClazzService campusNewsClazzService;

    @Autowired
    private ClazzService clazzService;

    @Autowired
    private CampusNewsClazzDisableService campusNewsClazzDisableService;

    private static final Logger log = LoggerFactory.getLogger("operationLog");

    @GetMapping("/info")
    @ResponseBody
    public void getInfo() {
        String ipAddress = serverInfo.getServerAddress();
        int serverPort = serverInfo.getServerPort();
        System.out.println(ipAddress);
        System.out.println(serverPort);
    }

    /**
     * @Description: 页面跳转-校园新闻
     * @data:[model]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/5/21 15:17
     */
    @GetMapping(path = "/campusNews")
    @MenuButton
    public String myDesk(Integer menuId, Model model) {
        model.addAttribute("menuId", menuId);
        return "pages/campusNewsManage/campusNews";
    }

    /**
     * @Description: 获取新闻用于客户端展示
     * @data:[]
     * @return: java.util.List<com.sxdzsoft.easyresource.form.CampusNewsVo2>
     * @Author: YangXiaoDong
     * @Date: 2023/5/30 10:45
     */
    @GetMapping("/getNews")
    @ResponseBody
    @IPCheck
    public List<CampusNewsVo2> getNews(Integer clazzId) {
        List<CampusNewsVo2> campusNews = campusNewService.queryAllNews(clazzId);
        return campusNews;
    }

    @GetMapping("/getNewsOne")
    @ResponseBody
    @IPCheck
    public CampusNewsVo2 getNewsOne(Integer guid) {
        CampusNews campusNews = campusNewService.queryById(guid);
        CampusNewsVo2 campusNewsVo2 = new CampusNewsVo2();
        BeanUtils.copyProperties(campusNews, campusNewsVo2);
        campusNewsVo2.setCover(campusNews.getImageAddress().getId().toString());
        campusNewsVo2.setContent(campusNews.getDetails());
        campusNewsVo2.setPublished_at(TimeFormatUtil.covertDateToString(campusNews.getTime()));
        return campusNewsVo2;
    }

    /**
     * @Description: 获取校园表单数据
     * @data:[campusNews, menuId, table]
     * @return: com.sxdzsoft.easyresource.domain.DataTableModel<com.sxdzsoft.easyresource.domain.CampusNews>
     * @Author: YangXiaoDong
     * @Date: 2023/5/16 9:20
     */
    @GetMapping("/queryCampusNews")
    @ResponseBody
    public DataTableModel<CampusNews> queryCampusNews(CampusNews campusNews, @RequestParam Integer menuId, DataTableModel<CampusNews> table) {
        DataTableModel<CampusNews> result = this.campusNewService.queryCampusNewse(campusNews, table);
        List<Menu> menus = this.menuService.queryMenuByParentIdAndTypeIsAndIsUseIs(menuId, 3, 1);
        result.setMenuBtns(menus);
        return result;
    }


    /**
     * @Description: 跳转新闻推送设备页面
     * @data:[]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/6/9 15:37
     */
    @GetMapping("/groupCampusNews")
    public String groupCampusNews(Model model,String newsId){
        List<Device> devices = deviceService.queryAllDeviceAndUseAndStatu();
        List<Clazz> clazzes = clazzService.queryAllClazzAndStar();
        model.addAttribute("newsId",newsId);
        model.addAttribute("devices",devices);
        model.addAttribute("clazzes",clazzes);
        return "pages/campusNewsManage/groupCampusNews";
    }

    /**
     * @Description: 数据修改推送
     * @data:[newsId]
     * @return: com.sxdzsoft.easyresource.form.ResultVo
     * @Author: YangXiaoDong
     * @Date: 2023/5/16 14:33
     */
    @PostMapping("/pushCampusNews")
    @ResponseBody
    public ResultVo pushCampusNews(String newsId,@RequestParam("members[]") String[] members,HttpSession session) {
        String[] split = newsId.split(",");
        CampusNewsVo2 campusNewsVo2 = new CampusNewsVo2();
        User user=(User)session.getAttribute("userinfo");

        for (String member : members) {
            List<CampusNewsClazz> campusNewsClazzes = campusNewsClazzService.queryByClazzId(Integer.valueOf(member));
            if (campusNewsClazzes.isEmpty()) {
                for (String s : split) {
                    CampusNewsClazz campusNewsClazz = new CampusNewsClazz();
                    campusNewsClazz.setClazzId(Integer.valueOf(member));
                    campusNewsClazz.setCampNewsId(Integer.valueOf(s));
                    campusNewsClazzService.changeNewsClazz(campusNewsClazz);
                }
            }else {
                for (int i = 0; i < split.length; i++) {

                }
            }
            for (String s : split) {
//                CampusNews campusNews1 = campusNewService.queryById(Integer.valueOf(s));
//                BeanUtils.copyProperties(campusNews1,campusNewsVo2);
//                campusNewsVo2.setCover(campusNews1.getImageAddress().getId().toString());
//                campusNewsVo2.setPublished_at(TimeFormatUtil.covertDateToString(campusNews1.getTime()));
//                WebsocketVo<CampusNewsVo2> websocketVo = new WebsocketVo<>();
//                Device device = deviceService.queryBymac(member);
//                CampusNewsClazz campusNewsClazz = new CampusNewsClazz();
//                campusNewsClazz.setCampNewsId(campusNews1.getId());
//                campusNewsClazz.setClazzId(device.getClazzId());
//                campusNewsClazzService.addCampusNewsClazz(campusNewsClazz);
//                webSocket.sendMessage(websocketVo.sendAll("pushCampusNews",campusNewsVo2),member);
//                log.info(user.getUsername()+"推送了新闻："+campusNews1.getTitle());

            }
        }

        ResultVo<Object> resultVo = new ResultVo<>();
        return resultVo;
    }

    /**
     * @Description: 更改新闻置顶状态
     * @data:[campusNewsClazz]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/7/1 14:44
     */
    @PostMapping("/changeNews")
    @ResponseBody
    public int changeNews(CampusNewsClazz campusNewsClazz){
        Integer campNewsId = campusNewsClazz.getCampNewsId();
        Integer clazzId = campusNewsClazz.getClazzId();
        CampusNews campusNews = campusNewService.queryById(campNewsId);
        if (campusNews.getTop()==1){
            return HttpResponseRebackCode.SameName;
        }
        CampusNewsClazzDisable campusNewsClazzDisable = campusNewsClazzDisableService.queryByCampNewsIdAndClazzId(campNewsId, clazzId);
        if (campusNewsClazzDisable!=null){
            return HttpResponseRebackCode.InValidate;
        }
        int res = campusNewsClazzService.changeNewsClazz(campusNewsClazz);
        if (res==1){
            List<Device> devices = deviceService.queryDeviceByClazzId(campusNewsClazz.getClazzId());
            for (Device device : devices) {
//                if (device.getStatu().equals(1)) {
//                    device.setFrequency(device.getFrequency() + 1);
//                    deviceService.changeNumber(device);
//                }
                webSocket.sendMessage(WebsocketVo.sendType("pushCampusNews"),device.getMacAddress());
            }
        }
        return res;
    }



    /**
     * @Description: 更改新闻展示状态
     * @data:[campusNewsClazzDisable]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/7/13 19:17
     */
    @PostMapping("/newsClazzDisable")
    @ResponseBody
    public int newsClazzDisable(CampusNewsClazzDisable campusNewsClazzDisable){
        CampusNews campusNews = campusNewService.queryById(campusNewsClazzDisable.getCampNewsId());
        if (campusNews.getTop()==1){
            return HttpResponseRebackCode.InValidate;
        }
        int res = campusNewsClazzDisableService.changeNewsClazz(campusNewsClazzDisable);
        if (res==1){
            List<Device> devices = deviceService.queryDeviceByClazzId(campusNewsClazzDisable.getClazzId());
            for (Device device : devices) {
//                if (device.getStatu().equals(1)) {
//                    device.setFrequency(device.getFrequency() + 1);
//                    deviceService.changeNumber(device);
//                }
                webSocket.sendMessage(WebsocketVo.sendType("pushCampusNews"),device.getMacAddress());
            }
        }
        return res;
    }



    /**
     * @Description: 新增新闻页面跳转
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/5/17 9:20
     */
    @GetMapping(path = "/addCampusNews")
    public String addTaskDialog(Model model) {
        return "pages/campusNewsManage/addCampusNews";
    }


    /**
     * @Description: 跳转表单修改页面
     * @data:[form2Id, model]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/2/23 17:09
     */
    @GetMapping("/editCampusNewsDialog")
    public String editCampusNewsDialog(int CampusNewsId, Model model) {
        CampusNews campusNews = campusNewService.queryById(CampusNewsId);
        model.addAttribute("campusNews", campusNews);
        return "pages/campusNewsManage/editCampusNews";
    }


    /**
     * @Description: 新增校园新闻
     * @data:[campusNewsVo]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/12 10:23
     */
    @PostMapping("/addCampusNews")
    @ResponseBody
    public int addCampusNews(CampusNewsVo campusNewsVo,HttpSession session) {
        CampusNews campusNews = new CampusNews();
        BeanUtils.copyProperties(campusNewsVo, campusNews);
        campusNews.setTime(TimeFormatUtil.convertStringToDate(campusNewsVo.getTime()));
        campusNews.setIsUse(1);
        MyFile myFile = myFileService.queryFileById(campusNewsVo.getTalkImage1());
        campusNews.setImageAddress(myFile);
        int res = campusNewService.addCampusNews(campusNews);
        if (res==1){
            webSocket.sendOpenAllUserMessage(WebsocketVo.sendType("pushCampusNews"));
            User user=(User)session.getAttribute("userinfo");
            log.info(user.getUsername()+"新增了新闻："+campusNewsVo.getTitle());
        }
        return res;
    }

    /**
     * @Description: 修改校园新闻
     * @data:[campusNews, multipartFiles]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/16 16:01
     */
    @PostMapping("/editCampusNews")
    @ResponseBody
    public int editCampusNews(HttpSession session,
                              @RequestParam("title") String title,
                              @RequestParam("campusNewsId") int campusNewsId,
                              @RequestParam("details") String details,
                              @RequestParam("time") String time,
                              @RequestPart(required = false, value = "topicImage") MultipartFile[] talkAddress) {
        CampusNews campusNews = new CampusNews(campusNewsId, title, details, 1);
        Date date = TimeFormatUtil.convertStringToDate(time);
        campusNews.setTime(date);
        int res = campusNewService.editCampusNews(campusNews, talkAddress);
        if (res==1){
            webSocket.sendOpenAllUserMessage(WebsocketVo.sendType("pushCampusNews"));
            User user=(User)session.getAttribute("userinfo");
            log.info(user.getUsername()+"修改了新闻："+title);
        }
        return res;
    }


    /**
     * @Description: 删除表单-校园新闻
     * @data:[campusNews]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/16 17:25
     */
    @PostMapping("/delCampusNews")
    @ResponseBody
    public int delCampusNews(CampusNews campusNews,HttpSession session) {
        CampusNews campusNews1 = campusNewService.delCampusNews(campusNews);
        if (campusNews1 != null) {
            webSocket.sendOpenAllUserMessage(WebsocketVo.sendType("pushCampusNews"));
            User user=(User)session.getAttribute("userinfo");
            log.info(user.getUsername()+"删除了新闻："+campusNews1.getTitle());
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }


    /**
     * @Description: 变更新闻置顶/取消置顶
     * @data:[]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/7/13 16:49
     */
    @PostMapping("/changeNewsTop")
    @ResponseBody
    public int changeNewsTop(CampusNews campusNews){
        int res = campusNewService.changeNewsTop(campusNews);
        if (res==1){
            webSocket.sendOpenAllUserMessage(WebsocketVo.sendType("pushCampusNews"));
        }
        return res;
    }


}
