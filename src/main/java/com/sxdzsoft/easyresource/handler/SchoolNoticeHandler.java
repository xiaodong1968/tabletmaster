package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.domain.SchoolNotice;
import com.sxdzsoft.easyresource.mapper.SchoolNoticeMapper;
import com.sxdzsoft.easyresource.service.SchoolNoticeService;
import com.sxdzsoft.easyresource.util.MenuButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/24 16:17
 * @PackageName:com.sxdzsoft.easyresource.handler
 * @ClassName: SchoolNoticeHandler
 * @Description: TODO
 * @Version 1.0
 */
@Controller
public class SchoolNoticeHandler {

    @Autowired
    private SchoolNoticeService schoolNoticeService;

    @Autowired
    private SchoolNoticeMapper schoolNoticeMapper;


    /**
     * @Description: 跳转校园通知页面
     * @data:[menuId, model]
     * @return: java.lang.String
     * @Author: YangXiaoDong
     * @Date: 2023/5/24 16:22
     */
    @GetMapping(path = "/schoolNotice")
    @MenuButton
    public String schoolNotice(Integer menuId, Model model) {
        model.addAttribute("menuId", menuId);
        return "pages/schoolNotice/schoolNoticeManage";
    }

    /**
     * @Description: 修改校园通知
     * @data:[content]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/24 16:43
     */
    @PostMapping("/addSchoolNotice")
    @ResponseBody
    public int addSchoolNotice(String content) {
        int res = schoolNoticeService.addSchoolNotice(content);
        return res;
    }



//    @PostMapping("/addSchoolNotice")
//    @ResponseBody
//    public int addSchoolNotice(String content) {
//        int i = schoolNoticeService.schoolNoticeUpdate(content);
//        if (i==1){
//            WebSocket.sendOpenAllUserMessage("schoolNotice");
//        }
//        return i;
//    }


    /**
     * @Description: 获取校园新闻的最新一条
     * @data:[]
     * @return: com.sxdzsoft.easyresource.domain.SchoolNotice
     * @Author: YangXiaoDong
     * @Date: 2023/5/31 15:15
     */
    @GetMapping("/schoolNoticeFirst")
    @ResponseBody
    public SchoolNotice schoolNoticeFirst() {
        SchoolNotice schoolNotice = schoolNoticeMapper.findFirst();
        return schoolNotice;
    }


}
