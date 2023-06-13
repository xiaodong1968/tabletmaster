package com.sxdzsoft.easyresource.handler;

import com.sxdzsoft.easyresource.domain.HttpResponseRebackCode;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;

/**
 * @Author YangXiaoDong
 * @Date 2023/5/10 10:27
 * @PackageName:com.sxdzsoft.easyresource.handler
 * @ClassName: WebSocketTest
 * @Description: TODO
 * @Version 1.0
 */
//@ServerEndpoint(value = "/websocketTest/{userId}")
//@Component
//@CrossOrigin
public class WebSocketTest {

    private static  WebSocketTest webSocketTest = null;

    private static Session session;

    //前端请求时一个websocket时
    @OnOpen
    public void onOpen(Session session1, @PathParam("userId") String userId) {
        session = session1;
        System.out.println("【websocket消息】有新的连接,连接id" + userId);
    }

    //前端关闭时一个websocket时
    @OnClose
    public void onClose() {
        session = null;
        System.out.println("Good bye,连接已经关闭");
    }

    //前端向后端发送消息
    @OnMessage
    public void onMessage(String message){
        System.out.println(session);
        if (!message.equals("ping")) {
            System.out.println("【websocket消息】收到客户端发来的消息:" + message);
        }
    }






}
