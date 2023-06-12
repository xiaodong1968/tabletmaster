package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.Clazz;
import com.sxdzsoft.easyresource.domain.DutyRoster;
import com.sxdzsoft.easyresource.domain.DutyRosterOption;
import com.sxdzsoft.easyresource.domain.HttpResponseRebackCode;
import com.sxdzsoft.easyresource.mapper.ClazzMapper;
import com.sxdzsoft.easyresource.mapper.DutyRosterMapper;
import com.sxdzsoft.easyresource.mapper.DutyRosterOptionMapper;
import com.sxdzsoft.easyresource.service.DutyRosterOptionService;
import com.sxdzsoft.easyresource.service.DutyRosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/30 14:27
 * @PackageName:com.sxdzsoft.easyresource.serviceImple
 * @ClassName: DutyRosterServiceImple
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class DutyRosterServiceImple implements DutyRosterService {

    @Autowired
    private DutyRosterMapper dutyRosterMapper;

    @Autowired
    private DutyRosterOptionMapper dutyRosterOptionMapper;

    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public int updateGruop() {
        List<DutyRoster> all = dutyRosterMapper.findAll();
        List<DutyRoster> updatedDutyRosters = new ArrayList<>();

        for (DutyRoster dutyRoster : all) {
            Integer dutyRosterId = dutyRoster.getId();
            Integer groupId = dutyRoster.getGroupId();
            List<DutyRosterOption> dutyRosterOptions = dutyRosterOptionMapper.queryByDutyRosterIdAndGruopBy(dutyRosterId);
            Integer sing = groupId + 1;
            boolean foundMatchingGroup = dutyRosterOptions.stream()
                    .anyMatch(dutyRosterOption -> (sing).equals(dutyRosterOption.getGroupId()));
            if (foundMatchingGroup) {
                dutyRoster.setGroupId(groupId + 1);
            } else {
                dutyRoster.setGroupId(1);
            }
            updatedDutyRosters.add(dutyRoster);
        }

        dutyRosterMapper.saveAll(updatedDutyRosters);

        return HttpResponseRebackCode.Ok;
    }

    @Override
    public int manualUpdateGroupByClazzId(Integer clazzId, Integer groupId) {
        Clazz clazz = clazzMapper.getById(clazzId);
        DutyRoster dutyRoster = clazz.getDutyRoster();
        dutyRoster.setGroupId(groupId);
        DutyRoster save = dutyRosterMapper.save(dutyRoster);
        if (save!=null){
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }
}
