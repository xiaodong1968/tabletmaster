package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.CampusNewsClazz;
import com.sxdzsoft.easyresource.domain.CampusNewsClazzDisable;
import com.sxdzsoft.easyresource.domain.HttpResponseRebackCode;
import com.sxdzsoft.easyresource.mapper.CampusNewsClazzDisableMapper;
import com.sxdzsoft.easyresource.mapper.CampusNewsClazzMapper;
import com.sxdzsoft.easyresource.service.CampusNewsClazzDisableService;
import com.sxdzsoft.easyresource.service.CampusNewsClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class CampusNewsClazzDisableServiceImple implements CampusNewsClazzDisableService {



    @Autowired
    private CampusNewsClazzDisableMapper campusNewsClazzDisableMapper;

    @Autowired
    private CampusNewsClazzMapper campusNewsClazzMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int changeNewsClazz(CampusNewsClazzDisable campusNewsClazzDisable) {
        if (campusNewsClazzDisable.getIsUse()==0){
            Integer campNewsId = campusNewsClazzDisable.getCampNewsId();
            Integer clazzId = campusNewsClazzDisable.getClazzId();
            CampusNewsClazzDisable campusNewsClazzDisable1 = campusNewsClazzDisableMapper.queryByCampNewsIdAndClazzId(campNewsId, clazzId);
            campusNewsClazzDisableMapper.delete(campusNewsClazzDisable1);
        }else {
            campusNewsClazzDisableMapper.save(campusNewsClazzDisable);
            Integer campNewsId = campusNewsClazzDisable.getCampNewsId();
            Integer clazzId = campusNewsClazzDisable.getClazzId();
            CampusNewsClazz campusNewsClazz = campusNewsClazzMapper.queryByCampNewsIdAndClazzId(campNewsId, clazzId);
            if (campusNewsClazz!=null){
                campusNewsClazzMapper.delete(campusNewsClazz);
            }
        }
        return HttpResponseRebackCode.Ok;
    }

    @Override
    public List<CampusNewsClazzDisable> queryByClazzId(Integer clazzId) {
        return campusNewsClazzDisableMapper.queryByClazzId(clazzId);
    }

    @Override
    public CampusNewsClazzDisable queryByCampNewsIdAndClazzId(Integer newsId, Integer clazzId) {
        return campusNewsClazzDisableMapper.queryByCampNewsIdAndClazzId(newsId, clazzId);
    }
}
