package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.Clazz;
import com.sxdzsoft.easyresource.domain.SchoolNotice;
import com.sxdzsoft.easyresource.service.SchoolNoticeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/3/10 13:26
 * @PackageName:com.sxdzsoft.easyresource.mapper
 * @ClassName: ClazzSpecification
 * @Description: TODO
 * @Version 1.0
 */
public class SchoolNoticeSpecification implements Specification<SchoolNotice> {

    SchoolNotice schoolNotice;



    public SchoolNoticeSpecification(SchoolNotice schoolNotice){
        this.schoolNotice = schoolNotice;
    }

    @Override

    public Predicate toPredicate(Root<SchoolNotice> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList();

        //名称不为空，根据名称进行模糊查询
        if(!StringUtils.isEmpty(schoolNotice.getTitle())) {
            predicates.add(criteriaBuilder.like(root.get("title").as(String.class),"%"+schoolNotice.getTitle()+"%"));
        }

        //如果时间不为空，根据时间区间进行查询
        if(!StringUtils.isEmpty(schoolNotice.getTmpTime())) {
            DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate finalFirstDayOfMonth = LocalDate.parse(schoolNotice.getTmpTime().substring(0, 10),timeDtf);
            LocalDate finalLastDayOfMonth = LocalDate.parse(schoolNotice.getTmpTime().substring(13),timeDtf);
            predicates.add(criteriaBuilder.between(root.get("createDate").as(LocalDate.class),finalFirstDayOfMonth,finalLastDayOfMonth));
        }

        //不查询已经删除的表单
        predicates.add(criteriaBuilder.notEqual(root.get("isUse").as(int.class),0));
        Predicate[] predicate = new Predicate[predicates.size()];
        return criteriaBuilder.and(predicates.toArray(predicate));
    }
}
