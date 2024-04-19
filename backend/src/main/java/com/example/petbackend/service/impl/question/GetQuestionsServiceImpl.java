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

import java.util.*;

@Service
public class GetQuestionsServiceImpl implements GetQuestionsService {

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    CateMapper cateMapper;
    @Autowired
    IllMapper illMapper;
    //按照题目搜索分页返回题目列表
    public Map<String, Object> getAllQuestionByDescription(Integer page, Integer pageSize, String key, Integer cate_id, Integer ill_id, Integer sort){
        //根据key，cate_id, ill_id来确定queryWrapper
        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        if (key != null && !key.isEmpty()) questionQueryWrapper.like("description", key);
        if (cate_id != -1) questionQueryWrapper.eq("cate_id", cate_id);
        if (ill_id != -1) questionQueryWrapper.eq("ill_id", ill_id);
        long total = questionMapper.selectCount(questionQueryWrapper);

        //按照题号排序
        if(sort == 0){
            List<QuestionDTO> questionDTOList = new ArrayList<>();
            IPage<Question> questionPage = new Page<>(page, pageSize);
            questionPage = questionMapper.selectPage(questionPage, questionQueryWrapper);
            List<Question> questionList = questionPage.getRecords();
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
            Map<String, Object> questionMap = new HashMap<>();
            if(questionDTOList != null && !questionDTOList.isEmpty()){ //搜到的题目列表不为空
                questionMap.put("error_message", "查询成功");
                questionMap.put("question_list", questionDTOList);
                questionMap.put("total", total);
            } else{
                questionMap.put("error_message", "未找到对应题目");
            }
            JSONObject obj = new JSONObject(questionMap);
            return obj;
        }
        //按照病名排序
        else{
            List<Question> questionList = questionMapper.selectList(questionQueryWrapper);
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
                pageMap.put("error_message", "查询成功");
                pageMap.put("question_list", pageList);
                pageMap.put("total", total);
            } else{
                pageMap.put("error_message", "未找到对应题目");
            }
            JSONObject obj = new JSONObject(pageMap);
            return obj;
        }


    }

    //按照病名搜索分页返回题目列表
    public Map<String, Object> getAllQuestionByIll(Integer page, Integer pageSize, String key, Integer sort){
        QueryWrapper<Ill> illQueryWrapper = new QueryWrapper<>();
        illQueryWrapper.like("ill_name", key);
        List<Ill> illList = illMapper.selectList(illQueryWrapper);
        List<Integer> illIdList = new ArrayList<>();
        for (Ill ill : illList) {
            illIdList.add(ill.getIllId());
        }
        long total = questionMapper.selectCount(Wrappers.<Question>lambdaQuery()
                .in(Question::getIllId, illIdList));

        //按题号排序
        if(sort == 0){
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
                questionDTOList.add(questionDTO);
            }
            Map<String, Object> questionMap = new HashMap<>();
            if(questionDTOList != null && !questionDTOList.isEmpty()){ //搜到的题目列表不为空
                questionMap.put("error_message", "查询成功");
                questionMap.put("question_list", questionDTOList);
                questionMap.put("total", total);
            } else{
                questionMap.put("error_message", "未找到对应题目");
            }
            JSONObject obj = new JSONObject(questionMap);
            return obj;
        }

        //按病名排序
        else{
            List<Question> questionList = questionMapper.selectList(Wrappers.<Question>lambdaQuery()
                    .in(Question::getIllId, illIdList));
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
                questionDTO.setAnswer(question.getAnswer());
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
                pageMap.put("error_message", "查询成功");
                pageMap.put("question_list", pageList);
                pageMap.put("total", total);
            } else{
                pageMap.put("error_message", "未找到对应题目");
            }
            JSONObject obj = new JSONObject(pageMap);
            return obj;

        }

    }

    //按照qid返回题目详情
    public Map<String, Object> getQuestionByQid(Integer qid) {
        Question question = questionMapper.selectById(qid);
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
        if (question != null) {
            questionMap.put("error_message", "查询成功");
            questionMap.put("question", questionDTO);
        } else {
            questionMap.put("error_message", "查看详情失败");
        }
        return questionMap;
    }
}
