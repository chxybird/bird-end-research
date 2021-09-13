package com.bird.service;

import com.bird.utils.JsonUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author lipu
 * @Date 2021/9/13 10:17
 * @Description ws具体业务
 */
@ServerEndpoint("/socket")
@Component
@Slf4j
@Data
public class WsService {

    //多线程安全的集合 存放每一个WebSocketServer
    private static final ConcurrentHashMap<String, WsService> concurrentHashMap = new ConcurrentHashMap<>();

    //当前Session
    private Session session;

    private static final String KET = "session_id";

    /**
     * @Author lipu
     * @Date 2020/7/31 11:28
     * @Description 连接建立成功执行此方法
     */
    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        //随机生成唯一会话id
        String id = session.getId();
        //存入容器
        concurrentHashMap.put(id, this);
        //向客户端发送消息 并将id返回给前端
        Map<String, String> map = new HashMap<>();
        map.put(KET, id);
        String json = JsonUtils.entityToJson(map);
        this.session.getBasicRemote().sendText(json);
        log.info(id + "加入服务");
    }

    /**
     * @Author lipu
     * @Date 2020/7/31 11:29
     * @Description 连接关闭后执行此方法
     */
    @OnClose
    public void onClose(Session session) throws IOException {
        //从容器中移除
        concurrentHashMap.remove(session.getId());
        //向客户端发送消息
        this.session.getBasicRemote().sendText("您已成功关闭连接");

    }

    /**
     * @Author lipu
     * @Date 2020/7/31 11:31
     * @Description 服务器端接收到消息执行此方法
     */
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        //接收客户端发送过来的消息
        log.info("客户端发送的消息：" + message);
    }

    /**
     * @Author lipu
     * @Date 2020/7/31 13:50
     * @Description 发生错误时执行此方法 ws会自动断开连接
     */
    @OnError
    public void onError(Throwable throwable) throws Exception {
        //从容器中移除
        concurrentHashMap.remove(session.getId());
        log.error("发生了未知错误，请重试");
    }


    /**
     * @Author lipu
     * @Date 2021/9/13 14:04
     * @Description 根据会话id向前端发送消息
     */
    public static void sendOne(String sessionId, String message) throws Exception{
        WsService wsService = concurrentHashMap.get(sessionId);
        if (wsService != null) {
            wsService.getSession().getBasicRemote().sendText(message);
        }
    }


}
