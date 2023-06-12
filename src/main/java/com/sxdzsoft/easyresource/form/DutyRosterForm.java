package com.sxdzsoft.easyresource.form;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/30 11:08
 * @PackageName:com.sxdzsoft.easyresource.form
 * @ClassName: DutyRosterForm
 * @Description: TODO
 * @Version 1.0
 */
@Data
public class DutyRosterForm {

    private List<Map<String, String>> rows;

    public List<Map<String, String>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, String>> rows) {
        this.rows = rows;
    }


}
