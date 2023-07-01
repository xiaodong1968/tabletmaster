package com.sxdzsoft.easyresource.mapper;

import com.sxdzsoft.easyresource.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/8 9:11
 * @PackageName:com.sxdzsoft.easyresource.mapper
 * @ClassName: DeviceMapper
 * @Description: TODO
 * @Version 1.0
 */
public interface DeviceMapper extends JpaRepository<Device, Integer>, JpaSpecificationExecutor<Device> {
    /**
     * @Description: 根据名称查找设备
     * @data:[name]
     * @return: com.sxdzsoft.easyresource.domain.Device
     * @Author: YangXiaoDong
     * @Date: 2023/6/8 16:06
     */
    public List<Device> queryByNameAndIsUse(String name,Integer isUse);


    /**
     * @Description: 根据设备上线状态来查找设备
     * @data:[isUse]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.Device>
     * @Author: YangXiaoDong
     * @Date: 2023/6/9 15:42
     */
    public List<Device> queryByIsUseAndStatu(Integer isUse,Integer statu);

    /**
     * @Description: 根据设备启用状态来查找设备
     * @data:[isUse]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.Device>
     * @Author: YangXiaoDong
     * @Date: 2023/6/9 15:42
     */
    public List<Device> queryByIsUse(Integer isUse);

    /**
     * @Description: 根据ip地址查找设备
     * @data:[ipAddress]
     * @return: com.sxdzsoft.easyresource.domain.Device
     * @Author: YangXiaoDong
     * @Date: 2023/6/8 16:07
     */
    public Device queryByIpAddress(String ipAddress);

    /**
     * @Description: 变更所有设备为离线状态
     * @data:[]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/9 9:49
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update Device SET statu = 2")
    public int changeDeviceOff();


    /**
     * @Description: 根据mac地址以及启用状态查找设备
     * @data:[macAddress, isUse]
     * @return: com.sxdzsoft.easyresource.domain.Device
     * @Author: YangXiaoDong
     * @Date: 2023/6/12 14:11
     */
    public Device queryByMacAddressAndIsUse(String macAddress,Integer isUse);


    /**
     * @Description: 根据班级以及启用状态查找设备
     * @data:[clazzId, isUse]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.Device>
     * @Author: YangXiaoDong
     * @Date: 2023/6/12 17:07
     */
    public List<Device> queryByClazzIdAndIsUse(Integer clazzId,Integer isUse);
 }
