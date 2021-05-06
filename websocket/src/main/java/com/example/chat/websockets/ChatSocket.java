package com.example.chat.websockets;

import com.example.chat.utils.RabbitProducer;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 *
 * @Description:
 * @Date: 2021/4/30 9:25
 * @Version: 1.0
 */
@ServerEndpoint(value = "/websocket/{name}/{password}")// websocket连接点映射.
@Component
public class ChatSocket {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收sid
    private String sid = "";

    static RabbitProducer rabbitProducer;

    //用来记录 key 用户唯一编码+应用授权码  和当前服务器session之间的绑定关系.
    private static Map<String, Session> map = new ConcurrentHashMap<>();

    //通过key获取session
    public static Session getSessionMapByKey(String key) {
        for (Map.Entry<String, Session> themap : map.entrySet()) {
            if (themap.getKey().equals(key)) {
                return themap.getValue();
            }
        }
        return null;
    }

    /**
     * 成功建立连接调用的方法.
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("name") String name, @PathParam("password") String password) {
        System.out.println("服务端接收到消息：");
        System.out.println(name);
        System.out.println(password);
        //将用户信息存储，视为  上线
        map.put(name,session);
    }

    /**
     * 连接关闭调用的方法.
     */

    @OnClose
    public void onClose() {

    }

    /*
     * 收到客户端消息后调用的方法.
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println(message);
        //将信息发送到消息队列
        rabbitProducer.sendMessages(message);


    }

    /**
     * 发生错误时调用.
     */
    @OnError
    public void onError(Session session, Throwable error) {

    }
}
