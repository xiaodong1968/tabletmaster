package com.sxdzsoft.easyresource.domain;

import lombok.Data;

/**
 * @ClassName JsTreeModel
 * @Description JSTREE数据模型
 * @Author wujian
 * @Date 2022/5/13 13:45
 * @Version 1.0
 **/
@Data
public class JsTreeModel {
    private String id;
    private String text;
    private boolean children;
    private String icon;
    private JsTreeModelState state;
}
