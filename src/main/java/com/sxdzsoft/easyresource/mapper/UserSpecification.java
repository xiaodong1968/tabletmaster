package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName RoleSpecification
 * @Description 用户数据过滤器
 * @Author wujian
 * @Date 2022/5/10 15:07
 * @Version 1.0
 **/

public class UserSpecification implements Specification<User> {
    private static final long serialVersionUID = 1L;
    User user;
    public UserSpecification(User user) {
        this.user=user;
    }
    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        //如果用户名称不为空，根据名称进行模糊查询
        if(user.getUsername()!=null) {
            predicates.add(criteriaBuilder.like(root.get("username").as(String.class), "%"+user.getUsername()+"%"));
        }
        //如果传递参数中isUse!=-1,则根据isUse进行查询
        if(user.getIsUse()!=-1) {
            predicates.add(criteriaBuilder.equal(root.get("isUse").as(Integer.class), user.getIsUse()));
        }
        //如果传递参数中isUse==-1,则表示查询除了已删除的用户
        if(user.getIsUse()==-1) {
            predicates.add(criteriaBuilder.notEqual(root.get("isUse").as(Integer.class), 0));
        }
        //只查询心理关爱老师
        if(user.getIsCare()==1) {

            predicates.add(criteriaBuilder.equal(root.get("isCare").as(Integer.class), user.getIsCare()));
        }
        //不查询超级管理员
        predicates.add(criteriaBuilder.notEqual(root.get("id").as(Integer.class), 1));
        Predicate[] predicate = new Predicate[predicates.size()];
        return criteriaBuilder.and(predicates.toArray(predicate));
    }

}