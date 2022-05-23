package com.sxdzsoft.easyresource.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DataTableModel
 * @Description DataTable模型，用于返回dataTable数据
 * @Author wujian
 * @Date 2022/5/10 14:57
 * @Version 1.0
 **/
@Data
public class DataTableModel<T> {
    private int draw;
    private int start;
    private int length;
    private int recordsFiltered;
    private int recordsTotal;
    private List<T> data=new ArrayList<T>();
    private List<Menu> menuBtns=new ArrayList<Menu>();
}
