package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.Group;
import com.sxdzsoft.easyresource.domain.MyTask;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName RoleSpecification
 * @Description 群组任务数据过滤器
 * @Author wujian
 * @Date 2022/5/10 15:07
 * @Version 1.0
 **/

public class GroupTaskSpecification implements Specification<MyTask> {
    private static final long serialVersionUID = 1L;
    MyTask task;
    List<Group> groups;
    public GroupTaskSpecification(MyTask task, List<Group> groups) {
        this.task=task;
        this.groups=groups;
    }
    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        //如果任务名称不为空，根据名称进行模糊查询
        if(task.getName()!=null) {
            predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%"+task.getName()+"%"));
        }
        //如果传递参数中isUse!=-1,则根据isUse进行查询
        if(task.getIsUse()!=-1) {
            predicates.add(criteriaBuilder.equal(root.get("isUse").as(Integer.class), task.getIsUse()));
        }
        //如果传递参数中isUse==-1,则表示查询除了已删除的任务
        if(task.getIsUse()==-1) {
            predicates.add(criteriaBuilder.notEqual(root.get("isUse").as(Integer.class), 0));
        }
        List<Integer> ids=groups.stream().map(Group::getId).collect(Collectors.toList());
        predicates.add (root.get("groupId").as(Integer.class).in(ids));
        Predicate[] predicate = new Predicate[predicates.size()];
        return criteriaBuilder.and(predicates.toArray(predicate));
    }

}