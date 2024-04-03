package com.example.petbackend.service.impl.question;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petbackend.dto.QuestionDTO;
import com.example.petbackend.mapper.CateMapper;
import com.example.petbackend.mapper.IllMapper;
import com.example.petbackend.mapper.QuestionMapper;
import com.example.petbackend.pojo.Cate;
import com.example.petbackend.pojo.Ill;
import com.example.petbackend.pojo.Question;
import com.example.petbackend.service.question.GetQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetQuestionsServiceImpl implements GetQuestionsService {

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    CateMapper cateMapper;
    @Autowired
    IllMapper illMapper;
    //按照题目搜索分页返回题目列表
    public Map<String, Object> getAllQuestionByDescription(Integer page, Integer pageSize, String key, Integer cate_id, Integer ill_id){
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        IPage<Question> questionPage = new Page<>(page, pageSize);

        //根据key，cate_id, ill_id来确定queryWrapper
        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        if (key != null && !key.isEmpty()) questionQueryWrapper.like("description", key);
        if (cate_id != -1) questionQueryWrapper.eq("cate_id", cate_id);
        if (ill_id != -1) questionQueryWrapper.eq("ill_id", ill_id);

        questionPage = questionMapper.selectPage(questionPage, questionQueryWrapper);
        List<Question> questionList = questionPage.getRecords();
        for(Question question: questionList){
            Cate cate = cateMapper.selectById(question.getCateId());
            Ill ill = illMapper.selectById(question.getIllId());
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQid(question.getQid());
            questionDTO.setCateID(question.getCateId());
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
        Map<String, Object> questionMap = new HashMap<>();
        if(questionDTOList != null && !questionDTOList.isEmpty()){ //搜到的题目列表不为空
            questionMap.put("error_message", "success");
            questionMap.put("question_list", questionDTOList);
        } else{
            questionMap.put("error_message", "未找到对应题目");
        }
        JSONObject obj = new JSONObject(questionMap);
        return obj;
    }

    //按照病名搜索分页返回题目列表
    public Map<String, Object> getAllQuestionByIll(Integer page, Integer pageSize, String key){
        QueryWrapper<Ill> illQueryWrapper = new QueryWrapper<>();
        illQueryWrapper.like("ill_name", key);
        List<Ill> illList = illMapper.selectList(illQueryWrapper);
        List<Integer> illIdList = new ArrayList<>();
        for (Ill ill : illList) {
            illIdList.add(ill.getIllId());
        }
        IPage<Question> questionPage = new Page<>(page, pageSize);
        questionPage = questionMapper.selectPage(questionPage, Wrappers.<Question>lambdaQuery()
                .in(Question::getIllId, illIdList));
        List<Question> questionList = questionPage.getRecords();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for(Question question: questionList){
            Cate cate = cateMapper.selectById(question.getCateId());
            Ill ill = illMapper.selectById(question.getIllId());
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setQid(question.getQid());
            questionDTO.setCateID(question.getCateId());
            questionDTO.setCateName(cate.getCateName());
            questionDTO.setIllId(question.getIllId());
            questionDTO.setIllName(ill.getIllName());
            questionDTO.setDescription((question.getDescription()));
            questionDTO.setMark(question.getMark());
            questionDTO.setAnswer(question.getAnswer());
            questionDTO.setContentA(question.getContentA());
            questionDTO.setContentB(question.getContentB());
            questionDTO.setContentC(question.getContentC());
            questionDTO.setContentD(question.getContentD());
            questionDTOList.add(questionDTO);
        }
        Map<String, Object> questionMap = new HashMap<>();
        if(questionDTOList != null && !questionDTOList.isEmpty()){ //搜到的题目列表不为空
            questionMap.put("error_message", "success");
            questionMap.put("question_list", questionDTOList);
        } else{
            questionMap.put("error_message", "未找到对应题目");
        }
        JSONObject obj = new JSONObject(questionMap);
        return obj;
    }

    //按照qid返回题目详情
    public Map<String, Object> getQuestionByQid(Integer qid) {
        Question question = questionMapper.selectById(qid);
        Map<String, Object> questionMap = new HashMap<>();
        if (question != null) {
            questionMap.put("error_message", "success");
            questionMap.put("question", question);
        } else {
            questionMap.put("error_message", "查看详情失败");
        }
        return questionMap;
    }
}
