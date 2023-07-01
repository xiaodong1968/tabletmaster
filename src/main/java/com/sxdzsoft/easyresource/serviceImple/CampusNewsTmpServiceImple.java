package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.CampusNews;
import com.sxdzsoft.easyresource.domain.CampusNewsClazz;
import com.sxdzsoft.easyresource.domain.CampusNewsTmp;
import com.sxdzsoft.easyresource.domain.HttpResponseRebackCode;
import com.sxdzsoft.easyresource.form.CampusNewsVo2;
import com.sxdzsoft.easyresource.form.ResultVo;
import com.sxdzsoft.easyresource.mapper.CampusNewsClazzMapper;
import com.sxdzsoft.easyresource.mapper.CampusNewsMapper;
import com.sxdzsoft.easyresource.mapper.CampusNewsTmpMapper;
import com.sxdzsoft.easyresource.service.CampusNewService;
import com.sxdzsoft.easyresource.service.CampusNewsTmpService;
import com.sxdzsoft.easyresource.util.TimeFormatUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/15 15:08
 * @PackageName:com.sxdzsoft.easyresource.serviceImple
 * @ClassName: CampusNewsTmpServiceImple
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class CampusNewsTmpServiceImple implements CampusNewsTmpService {

    @Autowired
    private CampusNewsTmpMapper campusNewsTmpMapper;

    @Autowired
    private CampusNewsMapper campusNewsMapper;

    @Autowired
    private CampusNewsClazzMapper campusNewsClazzMapper;

    @Override
    public List<CampusNewsVo2> queryCampusNewsTmp(Integer clazzId) {
        List<CampusNewsClazz> campusNewsClazzes = campusNewsClazzMapper.queryByClazzId(clazzId);
        List<CampusNews> campusNewsList = new ArrayList<>();
        if (campusNewsClazzes.isEmpty()) {
            campusNewsList = campusNewsMapper.queryByDeviceShow();
        } else {
            for (CampusNewsClazz campusNewsClazz : campusNewsClazzes) {
                CampusNews campusNews = campusNewsMapper.getById(campusNewsClazz.getCampNewsId());
                campusNewsList.add(campusNews);
            }
        }
        List<CampusNewsVo2> res = new ArrayList();
        for (CampusNews campusNews : campusNewsList) {
            CampusNewsVo2 campusNewsVo2 = new CampusNewsVo2();
            campusNewsVo2.setId(campusNews.getId());
            campusNewsVo2.setTitle(campusNews.getTitle());
            campusNewsVo2.setCover(campusNews.getImageAddress().getId().toString());
            campusNewsVo2.setContent(campusNews.getDetails());
            campusNewsVo2.setPublished_at(TimeFormatUtil.covertDateToString(campusNews.getTime()));
            res.add(campusNewsVo2);
        }
        return res;
    }

    @Override
    public ResultVo deleteAndinsert(List<CampusNews> campusNews) {
        Integer size = campusNews.size();
        if (!size.equals(5)) {
            int i = 1;
            for (CampusNews campusNew : campusNews) {
                CampusNewsTmp campusNewsTmp = campusNewsTmpMapper.findById(campusNew.getId()).orElse(null);
                if (campusNewsTmp != null) {
                    return ResultVo.fail(String.valueOf(i));
                }
                i++;
            }
        }

        Pageable pageable = PageRequest.of(0, size); // 创建分页对象
        Page<CampusNewsTmp> all = campusNewsTmpMapper.findAll(pageable);
        List<CampusNewsTmp> fruitList = all.getContent();// 这一页的所有记录
        for (CampusNewsTmp campusNewsTmp : fruitList) {
            campusNewsTmpMapper.deleteById(campusNewsTmp.getId());
        }
        for (CampusNews campusNew : campusNews) {
            CampusNewsTmp campusNewsTmp = new CampusNewsTmp();
            BeanUtils.copyProperties(campusNew, campusNewsTmp);
            campusNewsTmpMapper.save(campusNewsTmp);
        }
        return ResultVo.success("success");
    }

    @Override
    public int isContain(int CampusNewsTmpId) {
        CampusNewsTmp campusNewsTmp = campusNewsTmpMapper.findById(CampusNewsTmpId).orElse(null);
        if (campusNewsTmp != null) {
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }
}
