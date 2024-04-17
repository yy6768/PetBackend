package com.example.petbackend.consumer;

import com.example.petbackend.mapper.ExamMapper;
import com.example.petbackend.mapper.ExamUserMapper;
import com.example.petbackend.mapper.UserMapper;
import com.example.petbackend.pojo.Exam;
import com.example.petbackend.pojo.ExamUser;
import com.example.petbackend.pojo.User;
import com.example.petbackend.utils.ExamRedisUtil;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 前后端建立连接通信
 * 发送消息，后端实时判断信息并完成渲染
 */
@Component
@ServerEndpoint("/api/exam/{eu_id}")
public class WebSocketServer {

    // 用户列表
    final public static ConcurrentHashMap<Integer, WebSocketServer> users = new ConcurrentHashMap<>();
    private User user;
    private Exam exam;

    // 每个链接用session维护
    private Session session = null;

    private ExamRedisUtil redisUtil;

    // websocket 不是spring标准的单例模式，所以需要特殊处理
    public static UserMapper userMapper;

    public static ExamMapper examMapper;

    public static ExamUserMapper examUserMapper;



    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        WebSocketServer.userMapper = userMapper;
    }

    @Autowired
    public void setExamMapper(ExamMapper examMapper) {
        WebSocketServer.examMapper = examMapper;
    }

    @Autowired
    public void setExamUserMapper(ExamUserMapper examUserMapper) {
        WebSocketServer.examUserMapper = examUserMapper;
    }


    @Autowired
    public void setRedisUtil(ExamRedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    /**
     * 处理新建考试实体/重新恢复考试实体
     */
    private void startExam() {

    }


    /**
     * 处理上交试卷
     */
    private void endExam() {

    }


    //开启连接
    @OnOpen
    public void onOpen(Session session, @PathParam("eu_id") String idStr) throws IOException {
        this.session = session;

        try {
            int eu_id = Integer.parseInt(idStr);
            ExamUser examUser = examUserMapper.selectById(eu_id);
            this.user = userMapper.selectById(examUser.getUid());
            this.exam = examMapper.selectById(examUser.getExamId());
            if (this.user != null) {
                users.put(this.user.getUid(), this);
                System.out.println("connect!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.session.close();
        }

    }

    //关闭连接
    @OnClose
    public void onClose(Session session) {
        System.out.println("disconnected");
        users.remove(this.user.getUid());
    }

    /**
     * 接受消息
     * 分为几类消息：
     * 开始考试 startExam
     * 关闭考试 endExam
     * @param message 接受到的消息
     * @param session websocket的session信息
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.out.println("received " + message);
        if (message == null || message.isEmpty()) throw new IOException();

        if ("startExam".equalsIgnoreCase(message)) {
            // 处理新开始测试还是重新连接
            startExam();
        } else if ("endExam".equalsIgnoreCase(message)) {
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
        synchronized (this.session) {
            try {
                this.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
