package com.example.petbackend.service.impl.question;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petbackend.mapper.QuestionMapper;
import com.example.petbackend.pojo.Question;
import com.example.petbackend.service.question.GetQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetQuestionsServiceImpl implements GetQuestionsService {

    @Autowired
    QuestionMapper questionMapper;
    //带搜索值的分页获取题目列表
    public Map<String, Object> getAllQuestion(Integer page, Integer pageSize, String key){
        IPage<Question> questionPage = new Page<>(page, pageSize);
        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper.like("description", key);
        questionPage = questionMapper.selectPage(questionPage, questionQueryWrapper);
        List<Question> questionList = questionPage.getRecords();
        Map<String, Object> questionMap = new HashMap<>();
        if(questionList != null && !questionList.isEmpty()){ //搜到的题目列表不为空
            questionMap.put("error_message", "success");
            questionMap.put("question_list", questionList);
        } else{
            questionMap.put("error_message", "获取失败");
        }
        JSONObject obj = new JSONObject(questionMap);
        return obj;
    }
}
