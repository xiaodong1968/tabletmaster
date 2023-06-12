package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.CampusNewsClazz;
import com.sxdzsoft.easyresource.domain.HttpResponseRebackCode;
import com.sxdzsoft.easyresource.mapper.CampusNewsClazzMapper;
import com.sxdzsoft.easyresource.service.CampusNewsClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/9 18:07
 * @PackageName:com.sxdzsoft.easyresource.serviceImple
 * @ClassName: CampusNewsClazzServiceImple
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class CampusNewsClazzServiceImple implements CampusNewsClazzService {

    @Autowired
    private CampusNewsClazzMapper campusNewsClazzMapper;

    @Override
    public int addCampusNewsClazz(CampusNewsClazz campusNewsClazz) {
        CampusNewsClazz save = campusNewsClazzMapper.save(campusNewsClazz);
        if (save!=null){
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }
}
