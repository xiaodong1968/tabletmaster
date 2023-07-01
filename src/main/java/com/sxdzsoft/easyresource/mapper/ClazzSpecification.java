package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.Clazz;
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
public class ClazzSpecification implements Specification<Clazz> {

    Clazz clazz;



    public ClazzSpecification(Clazz clazz){
        this.clazz = clazz;
    }

    @Override

    public Predicate toPredicate(Root<Clazz> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList();

        //名称不为空，根据名称进行模糊查询
        if(!clazz.getClazzName().equals("")) {
            predicates.add(criteriaBuilder.like(root.get("clazzName").as(String.class),"%"+clazz.getClazzName()+"%"));
        }

        //班主任名称不为空，进行模糊查询
        if(!StringUtils.isEmpty(clazz.getClazzTeacher())){
            predicates.add(criteriaBuilder.like(root.get("clazzTeacher").as(String.class),"%"+clazz.getClazzTeacher()+"%"));

        }

        //如果传递参数中isUse!=-1,则根据isUse进行查询
        if(clazz.getIsUse()!=-1) {
            predicates.add(criteriaBuilder.equal(root.get("isUse").as(Integer.class), clazz.getIsUse()));
        }
        //如果传递参数中isUse==-1,则表示查询除了已删除的角色（包括正常启用和已经禁用的角色）
        if(clazz.getIsUse()==-1) {
            predicates.add(criteriaBuilder.notEqual(root.get("isUse").as(Integer.class), 0));
        }

        Predicate[] predicate = new Predicate[predicates.size()];
        return criteriaBuilder.and(predicates.toArray(predicate));
    }
}
