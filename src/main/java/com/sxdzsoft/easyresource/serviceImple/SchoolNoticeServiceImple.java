package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.HttpResponseRebackCode;
import com.sxdzsoft.easyresource.domain.SchoolNotice;
import com.sxdzsoft.easyresource.mapper.SchoolNoticeMapper;
import com.sxdzsoft.easyresource.service.SchoolNoticeService;
import com.sxdzsoft.easyresource.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/24 16:04
 * @PackageName:com.sxdzsoft.easyresource.serviceImple
 * @ClassName: SchoolNoticeServiceImple
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class SchoolNoticeServiceImple implements SchoolNoticeService {

    @Autowired
    private SchoolNoticeMapper schoolNoticeMapper;

    @Override
    public int addSchoolNotice(String content) {
        SchoolNotice schoolNotice = new SchoolNotice();
        schoolNotice.setContent(content);
        schoolNotice.setCreateDate(TimeUtil.getCurrentDate());
        SchoolNotice save = schoolNoticeMapper.save(schoolNotice);
        if (save!=null){
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }

    @Override
    public int schoolNoticeUpdate(String content) {
        SchoolNotice schoolNotice = new SchoolNotice();
        schoolNotice.setContent(content);
        SchoolNotice save = schoolNoticeMapper.save(schoolNotice);
        if (save!=null){
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }
}
