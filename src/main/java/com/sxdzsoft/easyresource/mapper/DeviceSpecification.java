package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.Device;
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
public class DeviceSpecification implements Specification<Device> {

    Device device;



    public DeviceSpecification(Device device){
        this.device = device;
    }

    @Override
    public Predicate toPredicate(Root<Device> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList();

        //名称不为空，根据名称进行模糊查询
        if(StringUtils.isNotBlank(device.getName())){
            predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%"+ device.getName()+"%"));
        }

        //不查询已经删除的
        predicates.add(criteriaBuilder.notEqual(root.get("isUse").as(int.class),0));
        Predicate[] predicate = new Predicate[predicates.size()];
        return criteriaBuilder.and(predicates.toArray(predicate));
    }
}
