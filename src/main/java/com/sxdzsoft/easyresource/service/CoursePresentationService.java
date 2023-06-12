package com.sxdzsoft.easyresource.service;

import com.sxdzsoft.easyresource.domain.CoursePresentation;

import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/22 13:59
 * @PackageName:com.sxdzsoft.easyresource.service
 * @ClassName: CoursePresentationService
 * @Description: TODO
 * @Version 1.0
 */
public interface CoursePresentationService {


    /**
     * @Description: 获取课程展示表信息
     * @data:[]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.CoursePresentation>
     * @Author: YangXiaoDong
     * @Date: 2023/5/23 8:57
     */
    public List<CoursePresentation> getByClazzId(Integer clazzId);


    /**
     * @Description: 更新课程展示表
     * @data:[coursePresentationIds]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/5/23 8:59
     */
    public int CoursePresentationUpdate(List<String> coursePresentationIds,Integer clazzId);





}
