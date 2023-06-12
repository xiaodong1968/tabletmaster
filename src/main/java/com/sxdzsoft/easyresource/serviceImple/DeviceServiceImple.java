package com.sxdzsoft.easyresource.serviceImple;

import com.sxdzsoft.easyresource.domain.*;
import com.sxdzsoft.easyresource.form.WebsocketVo;
import com.sxdzsoft.easyresource.handler.WebSocket;
import com.sxdzsoft.easyresource.mapper.ClazzMapper;
import com.sxdzsoft.easyresource.mapper.DeviceMapper;
import com.sxdzsoft.easyresource.mapper.DeviceSpecification;
import com.sxdzsoft.easyresource.service.ClazzService;
import com.sxdzsoft.easyresource.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author YangXiaoDong
 * @Date 2023/6/8 9:12
 * @PackageName:com.sxdzsoft.easyresource.serviceImple
 * @ClassName: DeviceServiceImple
 * @Description: TODO
 * @Version 1.0
 */
@Service
public class DeviceServiceImple implements DeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private ClazzMapper clazzMapper;

    @Autowired
    private WebSocket webSocket;

    @Override
    public DataTableModel<Device> queryDeviceForTable(Device device, DataTableModel<Device> table) {
        Page<Device> devices = this.deviceMapper.findAll(new DeviceSpecification(device), PageRequest.of(table.getStart() / table.getLength(), table.getLength()));
        DataTableModel<Device> result = new DataTableModel<Device>();
        result.setData(devices.getContent());
        result.setRecordsFiltered(Long.valueOf(devices.getTotalElements()).intValue());
        result.setRecordsTotal(devices.getNumberOfElements());
        return result;

    }

    @Override
    public int addEquipment(Device device) {
        if (device == null) {
            return HttpResponseRebackCode.Fail;
        }

        Device device1 = deviceMapper.queryByNameAndIsUse(device.getName(),1);
        if (device1 != null) {
            return HttpResponseRebackCode.SameName;
        }

        Device device2 = deviceMapper.queryByIpAddress(device.getIpAddress());
        if (device2 != null) {
            return HttpResponseRebackCode.SameName;
        }

        Device save = deviceMapper.save(device);
        if (save != null) {
            return HttpResponseRebackCode.Ok;
        }

        return HttpResponseRebackCode.Fail;
    }

    @Override
    public Device queryById(Integer deviceId) {
        Device device = deviceMapper.getById(deviceId);
        return device;
    }

    @Override
    public int deviceIsDisplay(Device device) {
        Device device1 = deviceMapper.queryByNameAndIsUse(device.getName(),1);
        //判断当前设备名是否相同
        if (device1!=null && device1.getId().intValue() != device.getId().intValue()){
            return HttpResponseRebackCode.SameName;
        }
//        Device device2 = deviceMapper.queryByIpAddress(device.getIpAddress());
//        //判断IP地址是否冲突
//        if (device2!=null && device2.getId().intValue() != device.getId().intValue()){
//            return HttpResponseRebackCode.SameName;
//        }
//        Device sing = deviceMapper.getById(device.getId());
//        sing.setName(device.getName());
//        sing.setIpAddress(device.getIpAddress());
//        sing.setMacAddress(device.getMacAddress());
//        sing.setClazzId(device.getClazzId());
//        Device save = deviceMapper.save(sing);
//        if (save!=null){
//            return HttpResponseRebackCode.Ok;
//        }
        return HttpResponseRebackCode.Ok;
    }

    @Override
    public int editDeviceShow(Device device) {
        Device sing = deviceMapper.getById(device.getId());
        sing.setName(device.getName());
        sing.setClazzId(device.getClazzId());
        Device save = deviceMapper.save(sing);
        if (save != null) {
            Clazz clazz = clazzMapper.getById(device.getClazzId());
            Clazz clazz1 = new Clazz();
            clazz1.setId(clazz.getId());
            clazz1.setClazzName(clazz.getClazzName());
            WebsocketVo<Clazz> websocketVo = new WebsocketVo<>();
            webSocket.sendMessage(websocketVo.sendAll("setingClazz",clazz1), save.getMacAddress());
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }

    @Override
    public int delDevice(Device device) {
        Device device1 = deviceMapper.getById(device.getId());
        device1.setIsUse(device.getIsUse());
        Device save = deviceMapper.save(device1);
        if (save != null) {
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }

    @Override
    public int changeDevice(String macAddress, Integer statu) {
        Device device = deviceMapper.queryByMacAddressAndIsUse(macAddress,1);
        device.setStatu(statu);
        Device save = deviceMapper.save(device);
        if (save!=null){
            return HttpResponseRebackCode.Ok;
        }
        return HttpResponseRebackCode.Fail;
    }

    @Override
    public int changeDeviceOff() {
        int res = deviceMapper.changeDeviceOff();
        return res;
    }

    @Override
    public List<Device> queryAllDeviceAndUseAndStatu() {
        List<Device> devices = deviceMapper.queryByIsUseAndStatu(1,1);
        return devices;
    }

    @Override
    public List<Device> queryAllDeviceAndUse() {
        List<Device> devices = deviceMapper.queryByIsUse(1);
        return devices;
    }

    @Override
    public int insertOrChangeDevice(Device device) {
        if (device == null) {
            return HttpResponseRebackCode.Fail;
        }
        //如果当前设备存在，将当前设备修改为在线状态
        Device device1 = deviceMapper.queryByMacAddressAndIsUse(device.getMacAddress(), 1);
        if (device1 != null) {
            device1.setStatu(1);
            device1.setIpAddress(device.getIpAddress());
            device1.setName(device.getName());
            deviceMapper.save(device1);
        }
        //如果当前设备不存在，则新增设备
        else {
            Clazz clazz = clazzMapper.queryByClazzNameAndIsUse(device.getName(), 1);
            device.setClazzId(clazz.getId());
            deviceMapper.save(device);
        }
        return HttpResponseRebackCode.Ok;
    }

    @Override
    public List<Device> queryDeviceByClazzId(Integer clazzId) {
        List<Device> devices = deviceMapper.queryByClazzIdAndIsUse(clazzId, 1);
        return devices;
    }

}
