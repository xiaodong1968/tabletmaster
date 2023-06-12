package com.sxdzsoft.easyresource.config;

import com.sxdzsoft.easyresource.handler.WebSocket;
import com.sxdzsoft.easyresource.service.DeviceService;
import com.sxdzsoft.easyresource.service.DutyRosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DailyTableUpdater {

    @Autowired
    private DutyRosterService dutyRosterService;

    @Autowired
    private DeviceService deviceService;


    @Scheduled(cron = "0 0 0 * * ?") // 每天凌晨12:00触发任务(修改班级值日表)
    public void updateDailyTable() {
        dutyRosterService.updateGruop();
        WebSocket.sendOpenAllUserMessage("updateGruop");
    }

    @Scheduled(fixedRate = 60000) // 每隔1分钟执行一次
    public void executeTask() {
        deviceService.changeDeviceOff();
    }
}

