package com.example.petbackend.consumer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.petbackend.dto.ExamRedisDTO;
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
import org.springframework.data.redis.core.RedisTemplate;
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
    private int eu_id;
    private User user;
    private Exam exam;

    // 每个链接用session维护
    private Session session = null;

    private static ExamRedisUtil redisUtil;

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
        boolean exists = redisUtil.containsKey(user.getUid().toString());
        if (!exists) {
            // 创建一个新的键值对

        } else {
            // sendMessage
        }


    }


    /**
     * 处理上交试卷
     */
    private void endExam() {

    }

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * 处理提交答案
     */
    private void addAnswer(String num, String option) {
        //根据uid和exam_id找到eu_id
        QueryWrapper<ExamUser> examUserQueryWrapper = new QueryWrapper<>();
        examUserQueryWrapper.eq("uid", user.getUid()).eq("exam_id", exam.getExamId());
        Integer eu_id = examUserMapper.selectOne(examUserQueryWrapper).getEuId();
        if(eu_id != null){
            //根据eu_id获取对应的DTO类
            ExamRedisDTO examRedisDTO = (ExamRedisDTO) redisTemplate.opsForValue().get("eu_id_" + eu_id);
            //更新此DTO的数据
            examRedisDTO.getAnswerMap().put(num, option);
            redisTemplate.opsForValue().set("eu_id_" + eu_id, examRedisDTO);
        }
    }



    //开启连接
    @OnOpen
    public void onOpen(Session session, @PathParam("eu_id") String idStr) throws IOException {
        this.session = session;

        try {
            this.eu_id = Integer.parseInt(idStr);
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

            startExam();
        } else if ("endExam".equalsIgnoreCase(message)) {
            endExam();
        } else if(message.toLowerCase().startsWith("answer")) {  //处理答案选项
            //解析消息内容
            String[] parts = message.split("\\s+");
            if(parts.length >= 3){
                String num = parts[1]; // 题号
                String option = parts[2]; // 选项
                addAnswer(num, option);
            } else{
                System.out.println(message);
                throw new MessageConversionException("websocket信息处理错误");
            }
        }
        else {
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
