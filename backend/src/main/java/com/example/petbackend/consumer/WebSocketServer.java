package com.example.petbackend.consumer;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * 前后端建立连接通信
 * 发送消息，后端实时判断信息并完成渲染
 */
@Component
@ServerEndpoint("/websocket/")
public class WebSocketServer {

    // 每个链接用session维护
    private Session session = null;




    private void startExam() {
    }


    //结束游戏
    public void endExam() {

    }


    //开启连接
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("connected");
    }

    //关闭连接
    @OnClose
    public void onClose(Session session) {
        System.out.println("disconnected");
    }

    /**
     * 接受消息
     * 分为几类消息：
     * 开始考试 startExam
     * 关闭考试 endExam
     * @param message 接受到的消息
     * @param session websocket的session信息
     * @throws IOException
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("received " + message);
        if (message == null || message.length() == 0) throw new IOException();

        if ("startGame".equals(message)) {
            startExam();
        } else if ("endGame".equalsIgnoreCase(message)) {
            endExam();
        } else {
            System.out.println(message);
            throw new MessageConversionException("websocket信息处理错误");
        }
    }




    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    /**
     * 向前端发送消息
     *
     * @param message 输出的消息
     */
    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
