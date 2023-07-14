package com.sxdzsoft.easyresource.mapper;



import com.sxdzsoft.easyresource.log.LoginLog;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 登录日志数据过滤
 * @Author wujian
 * @Date 14:34 2023/2/21
 * @Params
 * @Return
 **/

public class LoginLogSpecification implements Specification<LoginLog> {
    private static final long serialVersionUID = 1L;
    LoginLog loginLog;
    public LoginLogSpecification(LoginLog loginLog) {
        this.loginLog=loginLog;
    }
    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        if(!loginLog.getLevel_string().equals("-1")) {
            predicates.add(criteriaBuilder.equal(root.get("level_string").as(String.class), loginLog.getLevel_string()));
        }
        Predicate[] predicate = new Predicate[predicates.size()];
        return criteriaBuilder.and(predicates.toArray(predicate));
    }

}