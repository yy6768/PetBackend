package com.example.petbackend.service.impl.question;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petbackend.dto.QuestionDTO;
import com.example.petbackend.mapper.CateMapper;
import com.example.petbackend.mapper.IllMapper;
import com.example.petbackend.mapper.QuestionMapper;
import com.example.petbackend.pojo.Cate;
import com.example.petbackend.pojo.Ill;
import com.example.petbackend.pojo.Question;
import com.example.petbackend.service.question.SortQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SortQuestionServiceImpl implements SortQuestionService {

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    CateMapper cateMapper;
    @Autowired
    IllMapper illMapper;

    public Map<String, Object> sortQuestionByIll(String key, Integer page, Integer pageSize){
        List<Question> questionList = new ArrayList<>();
        if(key == null) questionList = questionMapper.selectList(null);
        else questionList = questionMapper.selectList(new QueryWrapper<Question>()
                .like("description", key));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for(Question question: questionList){
            Cate cate = cateMapper.selectById(question.getCateId());
            Ill ill = illMapper.selectById(question.getIllId());
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQid(question.getQid());
            questionDTO.setCateId(question.getCateId());
            questionDTO.setCateName(cate.getCateName());
            questionDTO.setIllId(question.getIllId());
            questionDTO.setIllName(ill.getIllName());
            questionDTO.setDescription((question.getDescription()));
            questionDTO.setMark(question.getMark());
            questionDTO.setAnswer((question.getAnswer()));
            questionDTO.setContentA(question.getContentA());
            questionDTO.setContentB(question.getContentB());
            questionDTO.setContentC(question.getContentC());
            questionDTO.setContentD(question.getContentD());
            questionDTOList.add(questionDTO);
        }
        questionDTOList.sort(Comparator.comparing
                (QuestionDTO :: getIllName, Comparator.nullsLast(Comparator.naturalOrder())));
        int totalSize = questionDTOList.size();
        int totalPages = (int) Math.ceil((double) totalSize / pageSize);

        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalSize);
        List<QuestionDTO> pageList = questionDTOList.subList(fromIndex, toIndex);

        Map<String, Object> pageMap = new HashMap<>();
        if(pageList != null && !pageList.isEmpty()){ //题目列表不为空
            pageMap.put("error_message", "success");
            pageMap.put("question_list", pageList);
        } else{
            pageMap.put("error_message", "未找到对应题目");
        }
        JSONObject obj = new JSONObject(pageMap);
        return obj;

    }
}
