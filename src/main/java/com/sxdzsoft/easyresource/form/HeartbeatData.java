package com.sxdzsoft.easyresource.form;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/9 9:31
 * @PackageName:com.sxdzsoft.easyresource.form
 * @ClassName: HeartbeatData
 * @Description: TODO
 * @Version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
public class HeartbeatData {
    private String type;
    private String macAddress;

    // 心跳数据的getter和setter方法


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}

