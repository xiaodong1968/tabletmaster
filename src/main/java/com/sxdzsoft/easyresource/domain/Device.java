package com.sxdzsoft.easyresource.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/8 9:05
 * @PackageName:com.sxdzsoft.easyresource.domain
 * @ClassName: Device
 * @Description: TODO
 * @Version 1.0
 */
@Entity
@Table(name="t_device_db")
@Data
@NoArgsConstructor
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,nullable = false)
    //主键ID
    private Integer id;
    //名称
    @Column
    private String name;
    //IP地址
    @Column
    private String ipAddress;
    //mac地址
    @Column
    private String macAddress;
    //状态
    @Column
    private Integer statu=2;
    //标记启用
    @Column
    private Integer isUse=1;

    @Column
    private Integer clazzId;

    @Transient
    private boolean isMember;

    public Device(String name, String ipAddress, String macAddress) {
        this.name = name;
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
    }
}
