package com.example.petbackend.service.impl.exam;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.petbackend.mapper.ExamMapper;
import com.example.petbackend.mapper.ExamUserMapper;
import com.example.petbackend.pojo.Exam;
import com.example.petbackend.pojo.ExamUser;
import com.example.petbackend.service.exam.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    ExamMapper examMapper;
    @Autowired
    ExamUserMapper examUserMapper;

    //添加一个考试
    @Override
    public Map<String, String> addExam(String exam_name, Integer paper_id, LocalDateTime begin_time, List<Integer> user_list){
        Exam exam = new Exam(paper_id, begin_time, exam_name);
        examMapper.insert(exam);
        int exam_id = exam.getExamId();
        //将user数组插入表
        for(int i = 0; i < user_list.size(); i++){
            ExamUser examUser = new ExamUser(exam_id,user_list.get(i),null);
            examUserMapper.insert(examUser);
        }
        Map<String, String> examMap= new HashMap<>();
        examMap.put("error_msg", "success");
        examMap.put("exam_id", String.valueOf(exam_id));
        return examMap;
    }

    //删除一个考试
    public Map<String, String> deleteExam(Integer exam_id){
        QueryWrapper<ExamUser> examUserQueryWrapper = new QueryWrapper<>();
        examUserQueryWrapper.eq("exam_id",exam_id);
        examUserMapper.delete(examUserQueryWrapper);
        int res = examMapper.deleteById(exam_id);
        Map<String, String> examMap = new HashMap<>();
        if(res<1){
            examMap.put("error_message", "delete fail");
        }
        else{
            examMap.put("error_message", "success");
        }
        return examMap;
    }

    //修改一个考试
    public Map<String, String> updateExam(Integer exam_id, String exam_name,
                                   Integer paper_id, LocalDateTime begin_time, List<Integer> user_list){
        Exam exam = examMapper.selectById(exam_id);
        Map<String, String> examMap = new HashMap<>();
        if(exam != null){
            exam.setExamName(exam_name);
            exam.setPaperId(paper_id);
            exam.setBeginTime(begin_time);
            int res = examMapper.updateById(exam);
            //把考试-用户表相关的记录更新
            QueryWrapper<ExamUser> examUserQueryWrapper = new QueryWrapper<>();
            examUserQueryWrapper.eq("exam_id", exam_id);
            examUserMapper.delete(examUserQueryWrapper);
            for(int i = 0; i < user_list.size(); i++){
                ExamUser examUser = new ExamUser(exam_id, user_list.get(i), null);
                examUserMapper.insert(examUser);
            }
            examMap.put("error_msg", "success");
        }else{
            examMap.put("error_msg", "delete fail");
        }
        return examMap;
    }
}
