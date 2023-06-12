package com.sxdzsoft.easyresource.form;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sxdzsoft.easyresource.util.MyDateFormat;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/17 9:32
 * @PackageName:com.sxdzsoft.easyresource.form
 * @ClassName: addCampusNewsVo
 * @Description: TODO
 * @Version 1.0
 */
@Data
public class CampusNewsVo {

    private String title;//标题名称

    private String details;//新闻详情

    private String time;//时间

    private int isUse;//状态

    private Integer talkImage1;

    private Integer talkImage2;
}
