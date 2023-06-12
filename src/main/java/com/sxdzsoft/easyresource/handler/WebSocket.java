package com.sxdzsoft.easyresource.handler;


import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.sxdzsoft.easyresource.domain.Device;
import com.sxdzsoft.easyresource.form.HeartbeatData;
import com.sxdzsoft.easyresource.service.DeviceService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket/{clazzName}/{ipAddress}/{macAddress}")
@CrossOrigin
@Component
public class WebSocket {

    private static ApplicationContext applicationContext;

    private  DeviceService deviceService;


    //解决无法注入mapper问题
    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocket.applicationContext = applicationContext;
    }

    private static ConcurrentHashMap<String, WebSocket> webSocketMap = new ConcurrentHashMap<>();
    //实例一个session，这个session是websocket的session
    private Session session;

    //新增一个方法用于主动向客户端发送消息
    public  void sendMessage(Object message, String macAddress) {
        WebSocket webSocket = webSocketMap.get(macAddress);
        if (webSocket != null) {
            try {
                webSocket.session.getBasicRemote().sendText(JSONUtil.toJsonStr(message));
                System.out.println("【websocket消息】发送消息成功,用户=" + macAddress + ",消息内容" + message.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





    //前端请求时一个websocket时
    @OnOpen
    public void onOpen(Session session,
                       @PathParam("clazzName") String clazzName,
                       @PathParam("ipAddress") String ipaddress,
                       @PathParam("macAddress") String macAddress) {

        this.session = session;
        webSocketMap.put(macAddress, this);
        //实例化bean
        deviceService = applicationContext.getBean(DeviceService.class);
        Device device = new Device(clazzName, ipaddress, macAddress);
        deviceService.insertOrChangeDevice(device);

    }




    @OnClose
    public void onClose(Session session) {
        // 遍历webSocketMap，找到对应的WebSocket实例并移除
        for (Map.Entry<String, WebSocket> entry : webSocketMap.entrySet()) {
            if (entry.getValue().session.equals(session)) {
                String userId = entry.getKey();
                webSocketMap.remove(userId);
                System.out.println("【websocket消息】连接断开,用户=" + userId + ",总数:" + webSocketMap.size());
                break;
            }
        }
    }


    //前端向后端发送消息
    @OnMessage
    public void onMessage(String message) throws IOException {
        System.out.println("接收到客户端发来的信息");
        HeartbeatData heartbeatData = JSON.parseObject(message, HeartbeatData.class);
        if ("heartbeat".equals(heartbeatData.getType())){
            deviceService.changeDevice(heartbeatData.getMacAddress(),1);
        }else {
            System.out.println("收到信息:" + message);
        }

    }

    public static List<String> getAllUser() {
        List<String> allUser = new LinkedList<>();
        Enumeration<String> keys = webSocketMap.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            allUser.add(key);
        }
        return allUser;
    }


    //群发消息
    public static void sendOpenAllUserMessage(String message) {
        for (WebSocket webSocket : webSocketMap.values()) {
            try {
                webSocket.session.getBasicRemote().sendText(message);

            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    public static void shutDown(String  userId){
        WebSocket webSocket = webSocketMap.get(userId);
        try {
            webSocket.session.getBasicRemote().sendText("shutDown");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        webSocketMap.remove(userId);
    }

    private static Object getFieldInstance(Object obj, String fieldPath) {
        String fields[] = fieldPath.split("#");
        for (String field : fields) {
            obj = getField(obj, obj.getClass(), field);
            if (obj == null) {
                return null;
            }
        }

        return obj;
    }

    private static Object getField(Object obj, Class<?> clazz, String fieldName) {
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                Field field;
                field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(obj);
            } catch (Exception e) {
            }
        }

        return null;
    }


}

