package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.form.*;
import com.sxdzsoft.easyresource.service.*;
import com.sxdzsoft.easyresource.util.MenuButton;
import com.sxdzsoft.easyresource.util.ServerInfo;
import com.sxdzsoft.easyresource.util.TimeFormatUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
    private CampusNewsTmpService campusNewsTmpService;


    @Autowired
    private MyFileService myFileService;


    @Autowired
    private DeviceService deviceService;

    @Autowired
    public ServerInfo serverInfo;

    @Autowired
    private WebSocket webSocket;

    @Autowired
    private ClazzService clazzService;

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
     * @Date: 2023/2/21 15:17
     */
    @GetMapping(path = "/campusNews")
    @MenuButton
    public String myDesk(Integer menuId, Model model) {
        model.addAttribute("menuId", menuId);
        return "pages/campusNewsManage/campusNews";
    }

    @GetMapping("/getNews")
    @ResponseBody
    public List<CampusNewsVo2> getNews() {
        List<CampusNewsVo2> campusNews = campusNewService.queryAllNews();
        return campusNews;
    }

    @GetMapping("/getNewsOne")
    @ResponseBody
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
        model.addAttribute("newsId",newsId);
        model.addAttribute("devices",devices);
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
    public ResultVo pushCampusNews(String newsId,@RequestParam("members[]") String[] members) {
        String[] split = newsId.split(",");
        CampusNewsVo2 campusNewsVo2 = new CampusNewsVo2();
        for (String member : members) {
            for (String s : split) {
                CampusNews campusNews1 = campusNewService.queryById(Integer.valueOf(s));
                BeanUtils.copyProperties(campusNews1,campusNewsVo2);
                campusNewsVo2.setCover(campusNews1.getImageAddress().getId().toString());
                campusNewsVo2.setPublished_at(TimeFormatUtil.covertDateToString(campusNews1.getTime()));
                WebsocketVo<CampusNewsVo2> websocketVo = new WebsocketVo<>();
                webSocket.sendMessage(websocketVo.sendAll("pushCampusNews",campusNewsVo2),member);
            }
        }

        ResultVo<Object> resultVo = new ResultVo<>();
        return resultVo;
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
    public int addCampusNews(CampusNewsVo campusNewsVo) {
        CampusNews campusNews = new CampusNews();
        BeanUtils.copyProperties(campusNewsVo, campusNews);
        campusNews.setTime(TimeFormatUtil.convertStringToDate(campusNewsVo.getTime()));
        campusNews.setIsUse(1);
//        List<MyFile> myFiles = new ArrayList<>();
//        if (!campusNewsVo.getTalkImage1().equals(0)) {
//            MyFile myFileImage1 = myFileService.queryFileById(campusNewsVo.getTalkImage1());
//            myFileImage1.setIsUse(1);
//            myFileImage1.setCampusNews(campusNews);
//            myFiles.add(myFileImage1);
//        }
//        if (!campusNewsVo.getTalkImage2().equals(0)) {
//            MyFile myFileImage2 = myFileService.queryFileById(campusNewsVo.getTalkImage2());
//            myFileImage2.setIsUse(1);
//            myFileImage2.setCampusNews(campusNews);
//            myFiles.add(myFileImage2);
//        }
        MyFile myFile = myFileService.queryFileById(campusNewsVo.getTalkImage1());
        campusNews.setImageAddress(myFile);
        return campusNewService.addCampusNews(campusNews);
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
    public int editCampusNews(@RequestParam("title") String title,
                              @RequestParam("campusNewsId") int campusNewsId,
                              @RequestParam("details") String details,
                              @RequestParam("time") String time,
                              @RequestPart(required = false, value = "topicImage") MultipartFile[] talkAddress) {
        CampusNews campusNews = new CampusNews(campusNewsId, title, details, 1);
        Date date = TimeFormatUtil.convertStringToDate(time);
        campusNews.setTime(date);
        return campusNewService.editCampusNews(campusNews, talkAddress);
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
    public int delCampusNews(CampusNews campusNews) {
        CampusNews campusNews1 = campusNewService.delCampusNews(campusNews);
        if (campusNews1 != null) {
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }



}
