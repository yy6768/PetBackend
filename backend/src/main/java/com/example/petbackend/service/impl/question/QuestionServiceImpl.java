package com.example.petbackend.service.impl.question;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petbackend.mapper.QuestionMapper;
import com.example.petbackend.pojo.Question;
import com.example.petbackend.service.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    //添加题目
    @Override
    public Map<String, String> addQuestion(Integer cate_id, Integer ill_id, String description,
                                           Integer answer, Integer mark, String content_a,
                                           String content_b, String content_c, String content_d){
        Question question = new Question(cate_id, ill_id, description, answer, mark,
                                         content_a, content_b, content_c, content_d);
        questionMapper.insert(question);
        Map<String, String> questionMap = new HashMap<>();
        questionMap.put("error_message", "success");
        questionMap.put("qid", String.valueOf(question.getQid()));
        return questionMap;
    }

    //修改题目
    @Override
    public Map<String, String> updateQuestion(Integer qid, Integer cate_id, Integer ill_id, String description,
                                              Integer answer, Integer mark, String content_a,
                                              String content_b, String content_c, String content_d){
        Question question = questionMapper.selectById(qid);
        int res = 0;
        if(question != null){
            question.setCateId(cate_id);
            question.setIllId(ill_id);
            question.setDescription(description);
            question.setAnswer(answer);
            question.setMark(mark);
            question.setContentA(content_a);
            question.setContentB(content_b);
            question.setContentC(content_c);
            question.setContentD(content_d);
            res = questionMapper.updateById(question);
        }
        Map<String, String> questionMap = new HashMap<>();
        if(res < 1){
            questionMap.put("error_message", "update fail");
        } else{
            questionMap.put("error_message", "success");
        }
        return questionMap;
    }

    //删除题目
    @Override
    public Map<String, String> deleteQuestion(Integer qid){
        int res = questionMapper.deleteById(qid);
        Map<String, String> questionMap = new HashMap<>();
        if(res < 1){
            questionMap.put("error_message", "delete fail");
        } else{
            questionMap.put("error_message", "success");
        }
        return questionMap;
    }


}
