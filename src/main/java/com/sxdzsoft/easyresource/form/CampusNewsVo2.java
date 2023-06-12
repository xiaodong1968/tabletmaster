package com.sxdzsoft.easyresource.form;

import lombok.Data;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/18 14:36
 * @PackageName:com.sxdzsoft.easyresource.form
 * @ClassName: CampusNewsVo2
 * @Description: TODO
 * @Version 1.0
 */
@Data
public class CampusNewsVo2 {
    private Integer id;
    private String title;
    private String cover;
    private String content;
    private String published_at;
}
