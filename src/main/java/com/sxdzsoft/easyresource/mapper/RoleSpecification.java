package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.Role;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName RoleSpecification
 * @Description 角色数据过滤器
 * @Author wujian
 * @Date 2022/5/10 15:07
 * @Version 1.0
 **/

public class RoleSpecification implements Specification<Role> {
    private static final long serialVersionUID = 1L;
    Role role;
    public RoleSpecification(Role role) {
        this.role=role;
    }
    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        //如果角色名称不为空，根据名称进行模糊查询
        if(role.getName()!=null) {
            predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%"+role.getName()+"%"));
        }
        //如果传递参数中isUse!=-1,则根据isUse进行查询
        if(role.getIsUse()!=-1) {
            predicates.add(criteriaBuilder.equal(root.get("isUse").as(Integer.class), role.getIsUse()));
        }
        //如果传递参数中isUse==-1,则表示查询除了已删除的角色（包括正常启用和已经禁用的角色）
        if(role.getIsUse()==-1) {
            predicates.add(criteriaBuilder.notEqual(root.get("isUse").as(Integer.class), 0));
        }
        //不查询超级管理员
        predicates.add(criteriaBuilder.notEqual(root.get("id").as(Integer.class), 1));
        Predicate[] predicate = new Predicate[predicates.size()];
        return criteriaBuilder.and(predicates.toArray(predicate));
    }

}