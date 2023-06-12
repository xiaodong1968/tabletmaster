package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.Clazz;
import com.sxdzsoft.easyresource.domain.Course;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
public class CourseSpecification implements Specification<Course> {

    Course course;



    public CourseSpecification(Course clazz){
        this.course = clazz;
    }

    @Override
    public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList();
        //标题不为空，根据标题进行模糊查询
        if(StringUtils.isNotBlank(course.getSubject())){
            predicates.add(criteriaBuilder.like(root.get("subject").as(String.class), "%"+course.getSubject()+"%"));
        }


        //不查询已经删除的表单
        predicates.add(criteriaBuilder.notEqual(root.get("isUse").as(int.class),0));
        Predicate[] predicate = new Predicate[predicates.size()];
        return criteriaBuilder.and(predicates.toArray(predicate));
    }
}
