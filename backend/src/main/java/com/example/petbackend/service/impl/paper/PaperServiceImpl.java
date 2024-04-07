package com.example.petbackend.service.impl.paper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.petbackend.mapper.PaperMapper;
import com.example.petbackend.mapper.PaperQuestionMapper;
import com.example.petbackend.pojo.Paper;
import com.example.petbackend.pojo.PaperQuestion;
import com.example.petbackend.service.paper.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    PaperMapper paperMapper;
    @Autowired
    PaperQuestionMapper paperQuestionMapper;

    //添加试卷
    @Override
    public Map<String, String> addPaper(Integer uid, String paper_name, Timestamp time, Date date, Integer[] question_list) {
        Paper paper = new Paper(paper_name, time, uid, date);
        paperMapper.insert(paper);
        int paper_id = paper.getPaperId();
        //将question_list里的数插入到paper_question表中
        for (Integer qid : question_list) {
            QueryWrapper<PaperQuestion> paperQuestionQueryWrapper = new QueryWrapper<>();
            paperQuestionQueryWrapper.eq("paper_id", paper_id);
            Integer currentNum = paperQuestionMapper.selectCount(paperQuestionQueryWrapper); // 查询当前记录数量
            PaperQuestion paperQuestion = new PaperQuestion(paper_id, qid, currentNum+1);
            paperQuestionMapper.insert(paperQuestion);
        }
        Map<String, String> paperMap = new HashMap<>();
        paperMap.put("error_message", "success");
        paperMap.put("paper_id", String.valueOf(paper_id));
        return paperMap;
    }

    //删除试卷
    @Override
    public Map<String, String> deletePaper(Integer paper_id){
        int res = paperMapper.deleteById(paper_id);
        QueryWrapper<PaperQuestion> paperQuestionQueryWrapper = new QueryWrapper<>();
        paperQuestionQueryWrapper.eq("paper_id", paper_id);
        paperQuestionMapper.delete(paperQuestionQueryWrapper);
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
    public Map<String, String> updatePaper(Integer uid, Integer paper_id, String paper_name, Timestamp time, Integer[] question_list){
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
            //把新的list加进去
            for (Integer qid : question_list) {
                QueryWrapper<PaperQuestion> Wrapper = new QueryWrapper<>();
                Wrapper.eq("paper_id", paper_id);
                Integer currentNum = paperQuestionMapper.selectCount(Wrapper); // 查询当前记录数量
                PaperQuestion paperQuestion = new PaperQuestion(paper_id, qid, currentNum+1);
                paperQuestionMapper.insert(paperQuestion);
            }
            paperMap.put("error_message", "success");

        }
        return paperMap;

    }
}
