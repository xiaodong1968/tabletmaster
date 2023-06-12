package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.CampusNews;
import com.sxdzsoft.easyresource.domain.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import javax.sound.midi.Soundbank;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/2/15 10:06
 * @PackageName:com.sxdzsoft.easyresource.mapper
 * @ClassName: MyForm2Specification
 * @Description: TODO
 * @Version 1.0
 */
public class CampusNewSpecification implements Specification<CampusNews> {


    CampusNews campusNews;


    public CampusNewSpecification(CampusNews campusNews) {
        this.campusNews = campusNews;
    }

    @Override
    public Predicate toPredicate(Root<CampusNews> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList();

        //标题不为空，根据标题进行模糊查询
        if(StringUtils.isNotBlank(campusNews.getTitle())){
            predicates.add(criteriaBuilder.like(root.get("title").as(String.class), "%"+campusNews.getTitle()+"%"));
        }

        //如果时间不为空，根据时间区间进行查询
        if(!campusNews.getStartTimeStr().equals("")) {
            DateTimeFormatter timeDtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate finalFirstDayOfMonth = LocalDate.parse(campusNews.getStartTimeStr().substring(0, 10),timeDtf);
            LocalDate finalLastDayOfMonth = LocalDate.parse(campusNews.getStartTimeStr().substring(13),timeDtf);
            predicates.add(criteriaBuilder.between(root.get("time").as(LocalDate.class),finalFirstDayOfMonth,finalLastDayOfMonth));
        }

        //不查询已经删除的表单
        predicates.add(criteriaBuilder.notEqual(root.get("isUse").as(int.class),0));
        Predicate[] predicate = new Predicate[predicates.size()];
        return criteriaBuilder.and(predicates.toArray(predicate));
    }
}