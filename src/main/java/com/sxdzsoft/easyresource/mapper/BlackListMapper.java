package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.BlackList;
import com.sxdzsoft.easyresource.domain.WhiteList;
import com.sxdzsoft.easyresource.service.BlackListService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/13 16:27
 * @PackageName:com.sxdzsoft.easyresource.mapper
 * @ClassName: WhiteListMapper
 * @Description: TODO
 * @Version 1.0
 */
public interface BlackListMapper extends JpaRepository<BlackList, Integer> {

    @Query("select b from BlackList b")
    public BlackList queryBlack();
}
