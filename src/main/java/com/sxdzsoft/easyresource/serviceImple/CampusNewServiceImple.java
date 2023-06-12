package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.form.CampusNewsVo2;
import com.sxdzsoft.easyresource.handler.WebSocket;
import com.sxdzsoft.easyresource.mapper.CampusNewSpecification;
import com.sxdzsoft.easyresource.mapper.CampusNewsMapper;
import com.sxdzsoft.easyresource.mapper.CampusNewsTmpMapper;
import com.sxdzsoft.easyresource.service.CampusNewService;
import com.sxdzsoft.easyresource.service.MyFileService;
import com.sxdzsoft.easyresource.util.ServerInfo;
import com.sxdzsoft.easyresource.util.TimeFormatUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/15 14:02
 * @PackageName:com.sxdzsoft.easyresource.serviceImple
 * @ClassName: queryCampusNewServiceImple
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class CampusNewServiceImple implements CampusNewService {

    @Autowired
    private CampusNewsMapper campusNewsMapper;
    @Autowired
    private MyFileService myFileService;
    @Autowired
    private CampusNewsTmpMapper campusNewsTmpMapper;
    @Autowired
    private ServerInfo serverInfo;



    @Override
    public DataTableModel<CampusNews> queryCampusNewse(CampusNews campusNews, DataTableModel<CampusNews> table) {
        Page<CampusNews> all = this.campusNewsMapper.findAll(new CampusNewSpecification(campusNews), PageRequest.of(table.getStart() / table.getLength(), table.getLength(), JpaSort.by("id").descending()));
        DataTableModel<CampusNews> result = new DataTableModel();
        result.setData(all.getContent());
        result.setRecordsFiltered(Long.valueOf(all.getTotalElements()).intValue());
        result.setRecordsTotal(all.getNumberOfElements());
        return result;
    }

    @Override
    public CampusNews queryById(Integer id) {
        return campusNewsMapper.getById(id);
    }

    @Override
    public int addCampusNews(CampusNews campusNews) {
        CampusNews campusNews2 = campusNewsMapper.queryByTitleAndIsUse(campusNews.getTitle(), 1);
        if (campusNews2 != null) {
            return HttpResponseRebackCode.SameName;
        }
        CampusNews save = campusNewsMapper.save(campusNews);
        if (save != null) {
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }

    @Override
    public int editCampusNews(CampusNews campusNews, MultipartFile[] multipartFiles) {
        CampusNews campusNews2 = campusNewsMapper.queryByTitleAndIsUse(campusNews.getTitle(), 1);
        if (campusNews2 != null && campusNews2.getId().intValue() != campusNews.getId().intValue()) {
            return HttpResponseRebackCode.SameName;
        }
        CampusNews campusNews1 = campusNewsMapper.getById(campusNews.getId());


        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                MyFile myFile = myFileService.uploadImage(multipartFile);
                campusNews1.setImageAddress(myFile);
            }

        }
        campusNews1.setTitle(campusNews.getTitle());
        campusNews1.setDetails(campusNews.getDetails());
        campusNews1.setIsUse(campusNews.getIsUse());
        campusNews1.setTime(campusNews.getTime());
        CampusNews save = campusNewsMapper.save(campusNews1);
        //判断新闻从表数据是否需要修改
        CampusNewsTmp sing = campusNewsTmpMapper.findById(campusNews.getId()).orElse(null);
        if (sing != null) {
            BeanUtils.copyProperties(campusNews1, sing);
            campusNewsTmpMapper.save(sing);
//            WebSocket.sendMessage("pushCampusNews","666");
        }
        if (save != null) {
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }

    @Override
    public CampusNews delCampusNews(CampusNews campusNews) {
        CampusNews sing = campusNewsMapper.getById(campusNews.getId());
        sing.setIsUse(campusNews.getIsUse());
        CampusNews save = campusNewsMapper.save(sing);
        return save;
    }

    @Override
    public List<CampusNewsVo2> queryAllNews() {
        List<CampusNews> all = campusNewsMapper.queryByIsUse(1);
        List<CampusNewsVo2> res = new ArrayList();
        for (CampusNews campusNews : all) {
            CampusNewsVo2 campusNewsVo2 = new CampusNewsVo2();
            BeanUtils.copyProperties(campusNews,campusNewsVo2);
            String ipAddress = serverInfo.getServerAddress();
            int serverPort = serverInfo.getServerPort();
            campusNewsVo2.setCover(campusNews.getImageAddress().getId().toString());
            campusNewsVo2.setContent(campusNews.getDetails());
            campusNewsVo2.setPublished_at(TimeFormatUtil.covertDateToString(campusNews.getTime()));
            res.add(campusNewsVo2);
        }
        return res;
    }
}
