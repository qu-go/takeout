package com.xinzhou.service.impl;

import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Set;
import java.util.concurrent.*;

////注册成组件
//@Component
////定义websocket服务器端，它的功能主要是将目前的类定义成一个websocket服务器端。注解的值将被用于监听用户连接的终端访问URL地址
//@ServerEndpoint("/websocket/{token}")
//如果不想每次都写private  final Logger logger = LoggerFactory.getLogger(当前类名.class); 可以用注解@Slf4j;可以直接调用log.info
@Slf4j
public class WebSocket extends TextWebSocketHandler {

    //实例一个session，这个session是websocket的session
    private Session session;
    private String token;


    //记录在线人数
    private static int onlineCount=0;

    //存放websocket的集合（本次demo不会用到，聊天室的demo会用到）
   // private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();
    private static ConcurrentHashMap<String,WebSocket> webSocketHm=new ConcurrentHashMap<>();
    //前端请求时一个websocket时
    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        this.session = session;
        this.token=token;
        webSocketHm.put(token,this);
        addOnlineCount();
        log.info("【websocket消息】有新的连接, 总数:{},session:{}", getOnlineCount(),session.getBasicRemote());
    }

    //前端关闭时一个websocket时
    @OnClose
    public void onClose() {
        webSocketHm.remove(token);
        removeOnlineCount();
        sendEvery();

        log.info("【websocket消息】连接断开, 总数:{}", getOnlineCount());
    }

    //前端向后端发送消息
    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】收到客户端发来的消息:{}", message);
        sendMessage("wwww");
    }

    //新增一个方法用于主动向客户端发送消息
    public static void sendMessage(String message) {
        for (WebSocket webSocket : webSocketHm.values()) {
            log.info("【websocket消息】广播消息, message={}", message);
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendEvery(){

        Runnable runnable = new Runnable() {
            @SneakyThrows
            @Override
            public void run() {

                    if (getOnlineCount()>0){
                        for (WebSocket value : webSocketHm.values()) {
                            value.session.getBasicRemote().sendText("记得早点睡交");
                        }
                    }
            }
        };

        ScheduledExecutorService service= Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable,5, 5, TimeUnit.SECONDS);


    }
    //在线人数增加
    public static synchronized void addOnlineCount(){
        onlineCount++;
    }
    public static synchronized void removeOnlineCount(){
        onlineCount--;
    }
    public static synchronized int getOnlineCount(){
        return WebSocket.onlineCount;
    }

}