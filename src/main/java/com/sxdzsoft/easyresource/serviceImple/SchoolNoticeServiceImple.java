package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.DataTableModel;
import com.sxdzsoft.easyresource.domain.HttpResponseRebackCode;
import com.sxdzsoft.easyresource.domain.SchoolNotice;
import com.sxdzsoft.easyresource.domain.SchoolNoticeClazz;
import com.sxdzsoft.easyresource.mapper.SchoolNoticeClazzMapper;
import com.sxdzsoft.easyresource.mapper.SchoolNoticeMapper;
import com.sxdzsoft.easyresource.mapper.SchoolNoticeSpecification;
import com.sxdzsoft.easyresource.service.SchoolNoticeService;
import com.sxdzsoft.easyresource.util.TimeFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private SchoolNoticeClazzMapper schoolNoticeClazzMapper;

    @Override
    public DataTableModel<SchoolNotice> querySchoolNoticeTable(SchoolNotice schoolNotice, DataTableModel<SchoolNotice> table) {
        Page<SchoolNotice> all = this.schoolNoticeMapper.findAll(new SchoolNoticeSpecification(schoolNotice), PageRequest.of(table.getStart() / table.getLength(), table.getLength(), JpaSort.by("id").descending()));
        DataTableModel<SchoolNotice> result = new DataTableModel();
        result.setData(all.getContent());
        result.setRecordsFiltered(Long.valueOf(all.getTotalElements()).intValue());
        result.setRecordsTotal(all.getNumberOfElements());
        return result;
    }

    @Override
    public int addSchoolNotice(SchoolNotice schoolNotice) {
        SchoolNotice schoolNotice1 = schoolNoticeMapper.queryByTitleAndIsUse(schoolNotice.getTitle(), 1);
        if (schoolNotice1 != null) {
            return HttpResponseRebackCode.SameName;
        }
        schoolNotice.setCreateDate(TimeFormatUtil.convertStringToDate(schoolNotice.getTmpTime()));
        SchoolNotice save = schoolNoticeMapper.save(schoolNotice);
        if (save != null) {
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }

    @Override
    public int editSchoolNotice(SchoolNotice schoolNotice) {
        SchoolNotice schoolNotice1 = schoolNoticeMapper.queryByTitleAndIsUse(schoolNotice.getTitle(), 1);
        if (schoolNotice1 != null && schoolNotice.getId().intValue() != schoolNotice1.getId().intValue()) {
            return HttpResponseRebackCode.SameName;
        }
        SchoolNotice schoolNotice2 = schoolNoticeMapper.getById(schoolNotice.getId());
        schoolNotice2.setTitle(schoolNotice.getTitle());
        schoolNotice2.setContent(schoolNotice.getContent());
        schoolNotice2.setCreateDate(TimeFormatUtil.convertStringToDate(schoolNotice.getTmpTime()));
        SchoolNotice save = schoolNoticeMapper.save(schoolNotice2);
        if (save != null) {
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }

    @Override
    public SchoolNotice querySchoolNoticeById(Integer schoolNoticeId) {
        SchoolNotice schoolNotice = schoolNoticeMapper.queryByIdAndIsUse(schoolNoticeId, 1);
        return schoolNotice;
    }

    @Override
    public List<SchoolNotice> queryAllNotice() {
        List<SchoolNotice> schoolNotices = schoolNoticeMapper.queryByIsUse(1);
        return schoolNotices;
    }

    @Override
    public SchoolNotice delSchoolNoticeById(Integer schoolNoticeId, Integer isUse) {
        SchoolNotice schoolNotice = schoolNoticeMapper.queryByIdAndIsUse(schoolNoticeId, 1);
        schoolNotice.setIsUse(isUse);
        SchoolNotice save = schoolNoticeMapper.save(schoolNotice);
        return save;
    }

    @Override
    public SchoolNotice getNoticeFirst(Integer clazzId) {
        SchoolNoticeClazz schoolNoticeClazz = schoolNoticeClazzMapper.queryByClazzId(clazzId);
        if (schoolNoticeClazz!=null){
            SchoolNotice schoolNotice = schoolNoticeMapper.getById(schoolNoticeClazz.getNoticeId());
            return schoolNotice;
        }else {
            SchoolNotice schoolNotice = schoolNoticeMapper.findFirst();
            return schoolNotice;
        }

    }
}
