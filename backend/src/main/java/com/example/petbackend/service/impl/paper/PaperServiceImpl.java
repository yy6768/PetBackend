package com.example.petbackend.service.impl.paper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.petbackend.mapper.ExamMapper;
import com.example.petbackend.mapper.ExamUserMapper;
import com.example.petbackend.mapper.PaperMapper;
import com.example.petbackend.mapper.PaperQuestionMapper;
import com.example.petbackend.pojo.Exam;
import com.example.petbackend.pojo.ExamUser;
import com.example.petbackend.pojo.Paper;
import com.example.petbackend.pojo.PaperQuestion;
import com.example.petbackend.service.paper.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    PaperMapper paperMapper;
    @Autowired
    PaperQuestionMapper paperQuestionMapper;
    @Autowired
    ExamMapper examMapper;
    @Autowired
    ExamUserMapper examUserMapper;

    //添加试卷
    @Override
    public Map<String, String> addPaper(Integer uid, String paper_name, Integer time, Date date, List<Integer> question_list) {
        Paper paper = new Paper(paper_name, time, uid, date);
        paperMapper.insert(paper);
        int paper_id = paper.getPaperId();
        // 插入试卷题目关联表
        for (int i = 0; i < question_list.size(); i++) {
            PaperQuestion paperQuestion = new PaperQuestion(paper_id, question_list.get(i), i + 1);
            paperQuestionMapper.insert(paperQuestion);
        }
        Map<String, String> paperMap = new HashMap<>();
        paperMap.put("error_message", "success");
        paperMap.put("paper_id", String.valueOf(paper_id));
        return paperMap;
    }

    //删除试卷，级联删除考试以及exam_user表的相关记录
    @Override
    public Map<String, String> deletePaper(Integer paper_id){
        //删除exam表以及exam_user表的级联数据
        QueryWrapper<Exam> examQueryWrapper = new QueryWrapper<>();
        examQueryWrapper.eq("paper_id", paper_id);
        List<Exam> examList = examMapper.selectList(examQueryWrapper);
        for(Exam exam: examList){
            QueryWrapper<ExamUser> examUserQueryWrapper = new QueryWrapper<>();
            examUserQueryWrapper.eq("exam_id", exam.getExamId());
            examUserMapper.delete(examUserQueryWrapper);
            examMapper.deleteById(exam);
        }

        QueryWrapper<PaperQuestion> paperQuestionQueryWrapper = new QueryWrapper<>();
        paperQuestionQueryWrapper.eq("paper_id", paper_id);
        paperQuestionMapper.delete(paperQuestionQueryWrapper);
        int res = paperMapper.deleteById(paper_id);
        Map<String, String> paperMap = new HashMap<>();
        if(res<1){
            paperMap.put("error_message", "delete fail");
        }
        else{
            paperMap.put("error_message", "success");
        }
        return paperMap;
    }

    //修改试卷
    @Override
    public Map<String, String> updatePaper(Integer uid, Integer paper_id, String paper_name, Integer time, List<Integer> question_list){
        Paper paper = paperMapper.selectById(paper_id);
        int auth = paper.getUid();
        Map<String, String> paperMap = new HashMap<>();
        if(uid != auth){
            paperMap.put("error_message", "不是试卷创始人无法修改试卷");
        }
        else {
            paper.setPaperName(paper_name);
            paper.setTime(time);
            int res = paperMapper.updateById(paper);
            //把原来的paper_question表里相关的记录删掉
            QueryWrapper<PaperQuestion> paperQuestionQueryWrapper = new QueryWrapper<>();
            paperQuestionQueryWrapper.eq("paper_id", paper_id);
            paperQuestionMapper.delete(paperQuestionQueryWrapper);
            // 把新的题目列表插进去
            for (int i = 0; i < question_list.size(); i++) {
                PaperQuestion paperQuestion = new PaperQuestion(paper_id, question_list.get(i), i + 1);
                paperQuestionMapper.insert(paperQuestion);
            }
            paperMap.put("error_message", "success");

        }
        return paperMap;

    }
}
