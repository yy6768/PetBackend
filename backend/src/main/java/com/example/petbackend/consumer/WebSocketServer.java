package com.example.petbackend.consumer;

import com.example.petbackend.dto.ExamRedisDTO;
import com.example.petbackend.mapper.ExamMapper;
import com.example.petbackend.mapper.ExamUserMapper;
import com.example.petbackend.mapper.PaperMapper;
import com.example.petbackend.mapper.UserMapper;
import com.example.petbackend.pojo.Exam;
import com.example.petbackend.pojo.ExamUser;
import com.example.petbackend.pojo.Paper;
import com.example.petbackend.pojo.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 前后端建立连接通信
 * 发送消息，后端实时判断信息并完成渲染
 */
@Component
@ServerEndpoint("/api/exam/{euId}")
public class WebSocketServer {

    // 用户列表
    final public static ConcurrentHashMap<Integer, WebSocketServer> users = new ConcurrentHashMap<>();
    private int euId;
    private User user;
    private Exam exam;

    // 每个链接用session维护
    private Session session = null;

    // websocket 不是spring标准的单例模式，所以需要特殊处理
    public static UserMapper userMapper;

    public static ExamMapper examMapper;

    public static ExamUserMapper examUserMapper;

    public static PaperMapper paperMapper;

    private static RedisTemplate<String, Object> redisTemplate;




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
    public void setPaperMapper(PaperMapper paperMapper) { WebSocketServer.paperMapper = paperMapper; }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        WebSocketServer.redisTemplate = redisTemplate;
    }

    /**
     * 创建新的Exam缓存信息
     * @param euId 当前Exam缓存的主键
     * @param uid 用户id
     * @param examId 考试id
     * @return 返回新建立的redis缓存信息
     */
    private ExamRedisDTO createNewExamRedisDTO(Integer euId, Integer uid, Integer examId) {
        ExamRedisDTO examRedisDTO = new ExamRedisDTO();
        examRedisDTO.setEuId(euId);
        examRedisDTO.setUid(uid);
        examRedisDTO.setExamId(examId);
        examRedisDTO.setTime(new Timestamp(System.currentTimeMillis()));
        examRedisDTO.setAnswerMap(new HashMap<>());  // 初始化一个空的答案映射
        return examRedisDTO;
    }

    /**
     * 序列化 DTO
     * @param examRedisDTO DTO实体
     * @return 序列化后的String
     * @throws JsonProcessingException 转义成
     */
    private String serializeExamRedisDTO(ExamRedisDTO examRedisDTO) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(examRedisDTO);
    }


    private Instant startEndExamTask() {
        Paper paper = paperMapper.selectById(exam.getPaperId());
        Duration duration = Duration.ofMillis(paper.getTime().getTime());
        LocalDateTime examStartTime = exam.getBeginTime();
        return examStartTime.plus(duration).atZone(ZoneId.systemDefault()).toInstant();
    }

    /**
     * 处理新建考试实体/重新恢复考试实体
     */
    private void startExam() {
        String key = "eu_id_" + euId;
        boolean exists = Boolean.TRUE.equals(redisTemplate.hasKey(key));
        if (!exists) {
            // 创建一个新ExamRedisDTO
            ExamRedisDTO examRedisDTO = createNewExamRedisDTO(euId, user.getUid(), exam.getExamId());
            // 存储到 Redis
            redisTemplate.opsForValue().set(key, examRedisDTO);
            // 启动定时任务
            startEndExamTask();
            // 向前端发送消息，表明已创建新的考试实例
            sendMessage("New exam started for EU_ID: " + euId);
        } else {
            // 从 Redis 中获取现有的 ExamRedisDTO
            ExamRedisDTO examRedisDTO = (ExamRedisDTO) redisTemplate.opsForValue().get(key);
            if (examRedisDTO != null) {
                // 序列化 ExamRedisDTO 发送给前端
                try {
                    String message = serializeExamRedisDTO(examRedisDTO);
                    sendMessage(message);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Error serializing exam data", e);
                }
            }
        }


    }




    /**
     * 处理上交试卷
     */
    public void endExam() {

    }

    /**
     * 处理提交答案
     */
    private void addAnswer(String num, String option) {
        //根据euId获取对应的DTO类
        ExamRedisDTO examRedisDTO = (ExamRedisDTO) WebSocketServer.redisTemplate.opsForValue().get("eu_id_" + euId);
        //更新此DTO的数据
        if(examRedisDTO != null) {
            examRedisDTO.getAnswerMap().put(num, option);
            WebSocketServer.redisTemplate.opsForValue().set("eu_id_" + euId, examRedisDTO);
            //向前端发送信息，表明已将答案存入缓存
            sendMessage("New answer stored in answerMap for EU_ID: " + euId);
        }
    }



    //开启连接
    @OnOpen
    public void onOpen(Session session, @PathParam("euId") String idStr) throws IOException {
        this.session = session;

        try {
            this.euId = Integer.parseInt(idStr);
            ExamUser examUser = examUserMapper.selectById(euId);
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
