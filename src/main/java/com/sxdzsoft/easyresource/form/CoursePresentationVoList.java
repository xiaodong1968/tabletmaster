package com.sxdzsoft.easyresource.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/22 17:06
 * @PackageName:com.sxdzsoft.easyresource.form
 * @ClassName: CoursePresentationVoList
 * @Description: TODO
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursePresentationVoList {
    private List<CoursePresentationVo> coursePresentationVos;
}
