package com.sxdzsoft.easyresource.domain;

import lombok.Data;

/**
 * @ClassName TaskPointRecord
 * @Description TODO
 * @Author wujian
 * @Date 2022/6/10 15:44
 * @Version 1.0
 **/
@Data
public class TaskPointRecord {
    private String name;//评分项
    private String zp;//自评分
    private String fh;//复核分
    private String fhuser;//复核人
    private String fhtime;//复核时间
    private String fhMessage;//复核意见
}
