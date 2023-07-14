package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.Clazz;
import com.sxdzsoft.easyresource.domain.DutyRoster;
import com.sxdzsoft.easyresource.domain.DutyRosterOption;
import com.sxdzsoft.easyresource.domain.HttpResponseRebackCode;
import com.sxdzsoft.easyresource.mapper.ClazzMapper;
import com.sxdzsoft.easyresource.mapper.DutyRosterMapper;
import com.sxdzsoft.easyresource.mapper.DutyRosterOptionMapper;
import com.sxdzsoft.easyresource.service.DutyRosterOptionService;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/30 14:28
 * @PackageName:com.sxdzsoft.easyresource.serviceImple
 * @ClassName: DutyRosterOptionServiceImple
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class DutyRosterOptionServiceImple implements DutyRosterOptionService {

    @Autowired
    private DutyRosterMapper dutyRosterMapper;

    @Autowired
    private DutyRosterOptionMapper dutyRosterOptionMapper;

    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int creatDutyRoster(List<String> data, Integer clazzId) {
        Clazz clazz = clazzMapper.getById(clazzId);
        DutyRoster dutyRosterRes = null;
        DutyRoster dutyRosterSing = clazz.getDutyRoster();
        if (dutyRosterSing != null) {
            List<DutyRosterOption> dutyRosterOptions = dutyRosterSing.getDutyRosterOptions();
            dutyRosterOptionMapper.deleteAll(dutyRosterOptions);
            dutyRosterRes = dutyRosterSing;
            dutyRosterRes.setGroupId(1);
        } else {
            DutyRoster dutyRoster = new DutyRoster();
            dutyRoster.setClazzName(clazz.getClazzName());
            dutyRoster.setGroupId(1);
            dutyRosterRes = dutyRosterMapper.save(dutyRoster);

        }
        clazz.setDutyRoster(dutyRosterRes);
        clazzMapper.save(clazz);
        for (int i = 0; i < data.size(); i++) {
            String[] split = data.get(i).split(",");
            for (String s : split) {
                DutyRosterOption dutyRosterOption = new DutyRosterOption(s, i + 1, dutyRosterRes);
                dutyRosterOptionMapper.save(dutyRosterOption);
            }
        }
        return HttpResponseRebackCode.Ok;
    }

    @Override
    public List<DutyRosterOption> getDutyRosterOption(Integer clazzId) {
        List<DutyRosterOption> dutyRosterOptions = new ArrayList<>();
        Clazz clazz = clazzMapper.getById(clazzId);
        if (clazz.getDutyRoster()==null){
            DutyRosterOption dutyRosterOption = new DutyRosterOption();
            dutyRosterOption.setGroupId(-1);
            dutyRosterOption.setName("当前班级暂未设置值日表");
            dutyRosterOptions.add(dutyRosterOption);
            return dutyRosterOptions;
        }
        DutyRoster dutyRoster = clazz.getDutyRoster();
        Hibernate.initialize(dutyRoster.getDutyRosterOptions());
        Integer groupId = dutyRoster.getGroupId();
        Integer dutyRosterId = dutyRoster.getId();
        dutyRosterOptions = dutyRosterOptionMapper.queryByDutyRosterIdAndGroupId(dutyRosterId, groupId);
        return dutyRosterOptions;
    }

    @Override
    public List<DutyRosterOption> queryByDutyRosterIdAndGruopBy(Integer clazzId) {
        List<DutyRosterOption> dutyRosterOptions = dutyRosterOptionMapper.queryByDutyRosterIdAndGruopBy(clazzId);
        return dutyRosterOptions;
    }
}
