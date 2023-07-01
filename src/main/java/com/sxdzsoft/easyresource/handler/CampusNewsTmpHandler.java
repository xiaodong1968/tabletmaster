package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.aspect.IPCheck;
import com.sxdzsoft.easyresource.form.CampusNewsVo2;
import com.sxdzsoft.easyresource.service.CampusNewsTmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/15 15:09
 * @PackageName:com.sxdzsoft.easyresource.handler
 * @ClassName: CampusNewsTmpHandler
 * @Description: TODO
 * @Version 1.0
 */
@Controller
public class CampusNewsTmpHandler {

    @Autowired
    private CampusNewsTmpService campusNewsTmpService;

    /**
     * @Description: 获取校园新闻从表数据
     * @data:[]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.CampusNewsTmp>
     * @Author: YangXiaoDong
     * @Date: 2023/5/16 14:12
     */
    @GetMapping("/queryCampusNewsHandlerAll")
    @ResponseBody
    @IPCheck
    public List<CampusNewsVo2> queryCampusNewsHandlerAll(Integer clazzId) {
        return campusNewsTmpService.queryCampusNewsTmp(clazzId);
    }


    /**
     * @Description: 根据id判断当前数据库是否存在此条数据
     * @data:[CampusNewsTmpId]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/16 14:16
     */
    @GetMapping("/isContain")
    @ResponseBody
    public int isContain(int CampusNewsTmpId){
        int res = campusNewsTmpService.isContain(CampusNewsTmpId);
        return res;
    }

}
