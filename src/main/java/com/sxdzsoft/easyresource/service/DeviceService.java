package com.sxdzsoft.easyresource.service;

import com.sxdzsoft.easyresource.domain.DataTableModel;
import com.sxdzsoft.easyresource.domain.Device;

import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/8 9:11
 * @PackageName:com.sxdzsoft.easyresource.service
 * @ClassName: DeviceService
 * @Description: TODO
 * @Version 1.0
 */
public interface DeviceService {

    /**
     * @Description: 获取设备的分页表格
     * @data:[device, table]
     * @return: com.sxdzsoft.easyresource.domain.DataTableModel<com.sxdzsoft.easyresource.domain.User>
     * @Author: YangXiaoDong
     * @Date: 2023/6/8 10:23
     */
    public DataTableModel<Device> queryDeviceForTable(Device device, DataTableModel<Device> table);


    /**
     * @Description: 新增设备
     * @data:[device]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/8 16:00
     */
    public int changeNumber(Device device);

    /**
     * @Description: 根据id查询设备
     * @data:[deviceId]
     * @return: com.sxdzsoft.easyresource.domain.Device
     * @Author: YangXiaoDong
     * @Date: 2023/6/8 18:06
     */
    public Device queryById(Integer deviceId);

    /**
     * @Description: 设备是否展示
     * @data:[device]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/8 18:12
     */
    public int deviceIsDisplay(Device device);


    /**
     * @Description: 更改设备
     * @data:[]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/12 16:30
     */
    public int editDeviceShow(Device device);


    /**
     * @Description: 删除设备
     * @data:[device]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/8 18:35
     */
    public int delDevice(Device device);

    /**
     * @Description: 变更设备状态
     * @data:[deviceId, statu]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/8 19:00
     */
    public int changeDevice(String macAddress,Integer statu);


    /**
     * @Description: 增加设备使用频率
     * @data:[macAddress, statu]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/7/14 14:07
     */
    public int changeFrequency(String macAddress,Integer statu);


    /**
     * @Description: 变更设备为离线状态
     * @data:[]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/9 9:46
     */
    public int changeDeviceOff();

    /**
     * @Description: 查询所有已上线的设备
     * @data:[]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.Device>
     * @Author: YangXiaoDong
     * @Date: 2023/6/9 15:40
     */
    public List<Device> queryAllDeviceAndUseAndStatu();


    /**
     * @Description: 查询已启用的设备
     * @data:[]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.Device>
     * @Author: YangXiaoDong
     * @Date: 2023/6/11 18:23
     */
    public List<Device> queryAllDeviceAndUse();

    /**
     * @Description: 通过建立连接新增设备
     * @data:[device]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/12 10:29
     */
    public int insertOrChangeDevice(Device device);


    /**
     * @Description: 根据班级查找对应的设备
     * @data:[clazzId]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/6/12 17:05
     */
    public List<Device> queryDeviceByClazzId(Integer clazzId);

    /**
     * @Description: 查询所有设备(包括离线的)
     * @data:[]
     * @return: java.util.List<com.sxdzsoft.easyresource.domain.Device>
     * @Author: YangXiaoDong
     * @Date: 2023/6/13 11:18
     */
    public List<Device> queryDevicesAndIsuse();

    /**
     * @Description: 通过mac地址查询
     * @data:[macAddress]
     * @return: com.sxdzsoft.easyresource.domain.Device
     * @Author: YangXiaoDong
     * @Date: 2023/6/30 16:12
     */
    public Device queryBymac(String macAddress);


    /**
     * @Description: 变更设备主题状态
     * @data:[device]
     * @return: int
     * @Author: YangXiaoDong
     * @Date: 2023/7/5 11:20
     */
    public int alterStyle(Device device);


    /**
     * @Description: 根据mac地址查询
     * @data:[macAddress]
     * @return: com.sxdzsoft.easyresource.domain.Device
     * @Author: YangXiaoDong
     * @Date: 2023/7/12 15:28
     */
    public Device queryByMacAddress(String macAddress);
}
