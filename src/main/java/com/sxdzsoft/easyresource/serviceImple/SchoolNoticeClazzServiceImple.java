package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.SchoolNoticeClazz;
import com.sxdzsoft.easyresource.mapper.SchoolNoticeClazzMapper;
import com.sxdzsoft.easyresource.mapper.SchoolNoticeMapper;
import com.sxdzsoft.easyresource.service.SchoolNoticeClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author YangXiaoDong
 * @Date 2023/7/1 21:48
 * @PackageName:com.sxdzsoft.easyresource.serviceImple
 * @ClassName: SchoolNoticeClazzServiceImple
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class SchoolNoticeClazzServiceImple implements SchoolNoticeClazzService {

    @Autowired
    private SchoolNoticeClazzMapper schoolNoticeClazzMapper;

    @Override
    public int changeNoticeClazz(SchoolNoticeClazz schoolNoticeClazz) {
        Integer clazzId = schoolNoticeClazz.getClazzId();
        Integer noticeId = schoolNoticeClazz.getNoticeId();
        SchoolNoticeClazz schoolNoticeClazz1 = schoolNoticeClazzMapper.queryByClazzId(clazzId);
        if (schoolNoticeClazz1!=null){
            schoolNoticeClazzMapper.delete(schoolNoticeClazz1);
        }
        schoolNoticeClazzMapper.save(schoolNoticeClazz);
        return 0;
    }
}
