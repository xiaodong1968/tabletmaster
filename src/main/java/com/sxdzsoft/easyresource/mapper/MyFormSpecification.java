package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.Group;
import com.sxdzsoft.easyresource.domain.MyForm;
import com.sxdzsoft.easyresource.domain.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName RoleSpecification
 * @Description 群组数据过滤器
 * @Author wujian
 * @Date 2022/5/10 15:07
 * @Version 1.0
 **/

public class MyFormSpecification implements Specification<MyForm> {
    private static final long serialVersionUID = 1L;
    MyForm form;
    public MyFormSpecification(MyForm form) {
        this.form=form;
    }
    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        //如果表单名称不为空，根据名称进行模糊查询
        if(form.getName()!=null) {
            predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%"+form.getName()+"%"));
        }
        //如果传递参数中isUse!=-1,则根据isUse进行查询
        if(form.getIsUse()!=-1) {
            predicates.add(criteriaBuilder.equal(root.get("isUse").as(Integer.class), form.getIsUse()));
        }
        //如果传递参数中isUse==-1,则表示查询除了已删除的表单
        if(form.getIsUse()==-1) {
            predicates.add(criteriaBuilder.notEqual(root.get("isUse").as(Integer.class), 0));
        }
        predicates.add(criteriaBuilder.equal(root.get("type").as(Integer.class), form.getType()));
        Join<MyForm, User> join=root.join("owner",JoinType.INNER);
        predicates.add(criteriaBuilder.equal(join.get("id").as(Integer.class),form.getOwner().getId().intValue()));
        Predicate[] predicate = new Predicate[predicates.size()];
        return criteriaBuilder.and(predicates.toArray(predicate));
    }

}