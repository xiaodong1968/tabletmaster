package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.CampusNews;
import com.sxdzsoft.easyresource.domain.CampusNewsClazz;
import com.sxdzsoft.easyresource.domain.HttpResponseRebackCode;
import com.sxdzsoft.easyresource.mapper.CampusNewsClazzMapper;
import com.sxdzsoft.easyresource.service.CampusNewsClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public int changeNewsClazz(CampusNewsClazz campusNewsClazz) {
        List<CampusNewsClazz> campusNewsClazzes = campusNewsClazzMapper.queryByClazzId(campusNewsClazz.getClazzId());
        if (campusNewsClazz.getIsUse()==0){
//            if (campusNewsClazzes.size()==1){
//                return HttpResponseRebackCode.lessMin;
//            }
            Integer campNewsId = campusNewsClazz.getCampNewsId();
            Integer clazzId = campusNewsClazz.getClazzId();
            CampusNewsClazz campusNewsClazz1 = campusNewsClazzMapper.queryByCampNewsIdAndClazzId(campNewsId, clazzId);
            campusNewsClazzMapper.delete(campusNewsClazz1);
        }else {
            if (campusNewsClazzes.size()==5){
                return HttpResponseRebackCode.overMax;
            }
            CampusNewsClazz save = campusNewsClazzMapper.save(campusNewsClazz);
        }
        return HttpResponseRebackCode.Ok;
    }

    @Override
    public List<CampusNewsClazz> queryByClazzId(Integer clazzId) {
        return campusNewsClazzMapper.queryByClazzId(clazzId);
    }
}
