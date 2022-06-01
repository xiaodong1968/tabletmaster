package com.sxdzsoft.easyresource.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName DirFilesModel
 * @Description TODO
 * @Author wujian
 * @Date 2022/5/19 14:27
 * @Version 1.0
 **/
@Data
public class DirFilesModel {
    private List<MyDir> dirs=new ArrayList<MyDir>();
    private List<MyFile> files=new ArrayList<MyFile>();
    private int parentId;
    private String currentName;
    @Override
    public String toString(){
        return this.currentName;
    }
}
