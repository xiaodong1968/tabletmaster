package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.Group;
import com.sxdzsoft.easyresource.domain.Role;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName RoleSpecification
 * @Description 群组数据过滤器
 * @Author wujian
 * @Date 2022/5/10 15:07
 * @Version 1.0
 **/

public class GroupSpecification implements Specification<Group> {
    private static final long serialVersionUID = 1L;
    Group group;
    public GroupSpecification(Group group) {
        this.group=group;
    }
    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        //如果群组名称不为空，根据名称进行模糊查询
        if(group.getName()!=null) {
            predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%"+group.getName()+"%"));
        }
        //如果传递参数中isUse!=-1,则根据isUse进行查询
        if(group.getIsUse()!=-1) {
            predicates.add(criteriaBuilder.equal(root.get("isUse").as(Integer.class), group.getIsUse()));
        }
        //如果传递参数中isUse==-1,则表示查询除了已删除的群组
        if(group.getIsUse()==-1) {
            predicates.add(criteriaBuilder.notEqual(root.get("isUse").as(Integer.class), 0));
        }
        Predicate[] predicate = new Predicate[predicates.size()];
        return criteriaBuilder.and(predicates.toArray(predicate));
    }

}