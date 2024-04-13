package com.example.petbackend.service.impl.paper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petbackend.dto.PaperDTO;
import com.example.petbackend.dto.QuestionOfPaperDTO;
import com.example.petbackend.mapper.PaperMapper;
import com.example.petbackend.mapper.PaperQuestionMapper;
import com.example.petbackend.mapper.QuestionMapper;
import com.example.petbackend.mapper.UserMapper;
import com.example.petbackend.pojo.Paper;
import com.example.petbackend.pojo.PaperQuestion;
import com.example.petbackend.pojo.Question;
import com.example.petbackend.pojo.User;
import com.example.petbackend.service.paper.GetPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetPaperServiceImpl implements GetPaperService {

    @Autowired
    PaperMapper paperMapper;
    @Autowired
    PaperQuestionMapper paperQuestionMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;

    //根据试卷id查询试卷
    public Map<String, Object> getPaperById(Integer paper_id){
        Paper paper = paperMapper.selectById(paper_id);
        Map<String, Object> paperMap = new HashMap<>();
        if(paper != null){
            paperMap.put("error_msg", "success");
            paperMap.put("paperId", paper.getPaperId());
            paperMap.put("paperName", paper.getPaperName());
            paperMap.put("time", paper.getTime());
            User user = userMapper.selectById(paper.getUid());
            paperMap.put("username", user.getUsername());
            paperMap.put("date", paper.getDate());
            //获取该试卷的题目列表
            QueryWrapper<PaperQuestion> paperQuestionQueryWrapper = new QueryWrapper<>();
            paperQuestionQueryWrapper.eq("paper_id", paper.getPaperId());
            List<PaperQuestion> paperQuestionList = paperQuestionMapper.selectList(paperQuestionQueryWrapper);
            List<QuestionOfPaperDTO> questionOfPaperDTOList = new ArrayList<>();
            for(PaperQuestion paperQuestion: paperQuestionList){
                QuestionOfPaperDTO questionOfPaperDTO = new QuestionOfPaperDTO();
                questionOfPaperDTO.setNum(paperQuestion.getNum());
                Question question = questionMapper.selectById(paperQuestion.getQid());
                questionOfPaperDTO.setDescription(question.getDescription());
                questionOfPaperDTO.setMark(question.getMark());
                questionOfPaperDTO.setContentA(question.getContentA());
                questionOfPaperDTO.setContentB(question.getContentB());
                questionOfPaperDTO.setContentC(question.getContentC());
                questionOfPaperDTO.setContentD(question.getContentD());
                questionOfPaperDTOList.add(questionOfPaperDTO);
            }
            paperMap.put("question_list", questionOfPaperDTOList);
        } else{
            paperMap.put("error_msg", "没找到对应的试卷");
        }
        return paperMap;
    }


    //根据试卷名搜索分页获取所有试卷
    public Map<String, Object> getAllPaper(Integer page, Integer pageSize, String key, Integer type){
        IPage<Paper> paperPage = new Page<>(page, pageSize);
        QueryWrapper<Paper> paperQueryWrapper = new QueryWrapper<>();
        if(type == 0){ //有关试卷名查找
            if(key != null && !key.isEmpty()) paperQueryWrapper.like("paper_name", key);
        }
        else if(type == 1){  //有关出卷人姓名查找
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            if(key != null && !key.isEmpty()) userQueryWrapper.like("username", key);
            List<User> userList = userMapper.selectList(userQueryWrapper);
            List<Integer> uidList = new ArrayList<>();
            for(User user: userList){
                uidList.add(user.getUid());
            }
            paperQueryWrapper.in("uid", uidList);
        }
        //计算总记录数
        Long total = paperMapper.selectCount(paperQueryWrapper);
        paperPage = paperMapper.selectPage(paperPage, paperQueryWrapper);
        List<Paper> paperList = paperPage.getRecords();
        List<PaperDTO> paperDTOList = new ArrayList<>();
        for(Paper paper : paperList){
            PaperDTO paperDTO = new PaperDTO();
            paperDTO.setUid(paper.getUid());
            paperDTO.setPaperName(paper.getPaperName());
            paperDTO.setPaperId(paper.getPaperId());
            paperDTO.setDate(paper.getDate());
            paperDTO.setTime(paper.getTime());
            //获取username
            User user = userMapper.selectById(paper.getUid());
            paperDTO.setUsername(user.getUsername());
            //求totalMark
            QueryWrapper<PaperQuestion> paperQuestionQueryWrapper = new QueryWrapper<>();
            paperQuestionQueryWrapper.eq("paper_id", paper.getPaperId());
            List<PaperQuestion> paperQuestionList = paperQuestionMapper.selectList(paperQuestionQueryWrapper);
            Integer totalMark = 0;
            for(PaperQuestion paperQuestion : paperQuestionList){
                Integer qid = paperQuestion.getQid();
                Question question = questionMapper.selectById(qid);
                totalMark = totalMark + question.getMark();
            }
            paperDTO.setTotalMark(totalMark);
            paperDTOList.add(paperDTO);
        }
        Map<String, Object> paperMap = new HashMap<>();
        if(paperDTOList != null && !paperDTOList.isEmpty()){
            paperMap.put("error_msg", "success");
            paperMap.put("paper_list", paperDTOList);
            paperMap.put("total", total);
        } else{
            paperMap.put("error_msg", "未找到对应试卷");
        }
        JSONObject obj = new JSONObject(paperMap);
        return obj;
    }

}
