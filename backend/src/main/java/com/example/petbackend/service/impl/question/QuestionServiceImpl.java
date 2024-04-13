package com.example.petbackend.service.impl.question;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.petbackend.dto.QuestionDTO;
import com.example.petbackend.mapper.CateMapper;
import com.example.petbackend.mapper.IllMapper;
import com.example.petbackend.mapper.PaperQuestionMapper;
import com.example.petbackend.mapper.QuestionMapper;
import com.example.petbackend.pojo.Cate;
import com.example.petbackend.pojo.Ill;
import com.example.petbackend.pojo.PaperQuestion;
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
    @Autowired
    CateMapper cateMapper;
    @Autowired
    IllMapper illMapper;
    @Autowired
    PaperQuestionMapper paperQuestionMapper;

    //添加题目
    @Override
    public Map<String, Object> addQuestion(Integer cate_id, Integer ill_id, String description,
                                           Integer answer, Integer mark, String content_a,
                                           String content_b, String content_c, String content_d){
        Question question = new Question(cate_id, ill_id, description, answer, mark,
                                         content_a, content_b, content_c, content_d);
        questionMapper.insert(question);
        Cate cate = cateMapper.selectById(cate_id);
        Ill ill = illMapper.selectById(ill_id);
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setQid(question.getQid());
        questionDTO.setCateId(cate_id);
        questionDTO.setCateName(cate.getCateName());
        questionDTO.setIllId(ill_id);
        questionDTO.setIllName(ill.getIllName());
        questionDTO.setDescription(description);
        questionDTO.setMark(mark);
        questionDTO.setAnswer(answer);
        questionDTO.setContentA(content_a);
        questionDTO.setContentB(content_b);
        questionDTO.setContentC(content_c);
        questionDTO.setContentD(content_d);
        Map<String, Object> questionMap = new HashMap<>();
        questionMap.put("error_message", "success");
        questionMap.put("question", questionDTO);
        return questionMap;
    }

    //修改题目
    @Override
    public Map<String, Object> updateQuestion(Integer qid, Integer cate_id, Integer ill_id, String description,
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
        QuestionDTO questionDTO = new QuestionDTO();
        Cate cate = cateMapper.selectById(question.getCateId());
        Ill ill = illMapper.selectById(question.getIllId());
        questionDTO.setQid(question.getQid());
        questionDTO.setCateId(question.getCateId());
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
        Map<String, Object> questionMap = new HashMap<>();
        if(res < 1){
            questionMap.put("error_message", "update fail");
        } else{
            questionMap.put("error_message", "success");
            questionMap.put("question", questionDTO);
        }
        return questionMap;
    }

    //删除题目，级联删除，要删除paper_question中的相关记录
    @Override
    public Map<String, String> deleteQuestion(Integer qid){
        //级联
        QueryWrapper<PaperQuestion> paperQuestionQueryWrapper = new QueryWrapper<>();
        paperQuestionQueryWrapper.eq("qid", qid);
        paperQuestionMapper.delete(paperQuestionQueryWrapper);

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
