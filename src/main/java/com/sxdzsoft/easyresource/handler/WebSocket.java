package com.sxdzsoft.easyresource.handler;


import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.sxdzsoft.easyresource.domain.Device;
import com.sxdzsoft.easyresource.form.HeartbeatData;
import com.sxdzsoft.easyresource.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
@Slf4j
public class WebSocket implements WebSocketHandler {



    private DeviceService deviceService;

    private static ApplicationContext applicationContext;


    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocket.applicationContext = applicationContext;
    }

    private static ConcurrentHashMap<String, WebSocket> webSocketMap = new ConcurrentHashMap<>();

    //实例一个session，这个session是websocket的session
    private WebSocketSession session;

    /**
     * @Description: 新增一个方法用于主动向客户端发送消息
     * @data:[message, macAddress]
     * @return: void
     * @Author: YangXiaoDong
     * @Date: 2023/6/14 15:21
     */
    public  void sendMessage(Object message, String macAddress) {
        WebSocket webSocket = webSocketMap.get(macAddress);
        if (webSocket != null) {
            try {
                webSocket.session.sendMessage(new TextMessage(JSONUtil.toJsonStr(message)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    //群发消息
    public static void sendOpenAllUserMessage(String message) {
        for (WebSocket webSocket : webSocketMap.values()) {
            try {
                webSocket.session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }




    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        this.session = session;
        // 获取路径参数
        String clazzName = session.getAttributes().get("clazzName").toString();
        String ipAddress = session.getAttributes().get("ipAddress").toString();
        String macAddress = session.getAttributes().get("macAddress").toString();

        // 存储连接
        webSocketMap.put(macAddress, this);
        Device device = new Device(clazzName, ipAddress, macAddress);
        deviceService = applicationContext.getBean(DeviceService.class);
        deviceService.insertOrChangeDevice(device);

        System.out.println("【websocket消息】新的连接,用户=" + ipAddress+ ",总数:" + webSocketMap.size());
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)  {

        if (message.getPayload() instanceof String) {
            String payload = (String) message.getPayload();
            System.out.println("收到信息: " + payload);

            // 解析 JSON 字符串为心跳数据对象
            HeartbeatData heartbeatData = JSON.parseObject(payload, HeartbeatData.class);

            if (heartbeatData != null && "heartbeat".equals(heartbeatData.getType())) {
                // 处理心跳消息
                deviceService.changeDevice(heartbeatData.getMacAddress(), 1);
            }
        } else {
            System.out.println("收到未知类型的信息:" + message);
        }
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        // 遍历webSocketMap，找到对应的WebSocket实例并移除
        for (Map.Entry<String, WebSocket> entry : webSocketMap.entrySet()) {
            if (entry.getValue().session.equals(session)) {
                String userId = entry.getKey();
                webSocketMap.remove(userId);
                //切换在线设备状态
                deviceService.changeDevice(userId,2);
                System.out.println("【websocket消息】连接断开,用户=" + userId + ",总数:" + webSocketMap.size());
                break;
            }
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}

