package com.example.petbackend.service.impl.exam;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petbackend.dto.ExamDTO;
import com.example.petbackend.dto.ExamUserDTO;
import com.example.petbackend.mapper.*;
import com.example.petbackend.pojo.*;
import com.example.petbackend.service.exam.GetExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetExamServiceImpl implements GetExamService {

    @Autowired
    ExamMapper examMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    PaperMapper paperMapper;
    @Autowired
    PaperQuestionMapper paperQuestionMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    ExamUserMapper examUserMapper;

    //根据考试名搜索分页获取所有考试
    public Map<String, Object> getAllExamByName(Integer page, Integer pageSize, String key){
        Map<String, Object> examMap = new HashMap<>();
        IPage<Exam> examIPage = new Page<>(page, pageSize);
        QueryWrapper<Exam> examQueryWrapper = new QueryWrapper<>();
        if(key != null && !key.isEmpty()) examQueryWrapper.like("exam_name", key);
        //计算搜到的总记录数
        Long total = examMapper.selectCount(examQueryWrapper);
        //获取分页
        examIPage = examMapper.selectPage(examIPage, examQueryWrapper);
        List<Exam> examList = examIPage.getRecords();
        List<ExamDTO> examDTOList = new ArrayList<>();
        for(Exam exam: examList){
            ExamDTO examDTO = new ExamDTO();
            examDTO.setExamName(exam.getExamName());
            examDTO.setExamId(exam.getExamId());
            examDTO.setBeginTime(exam.getBeginTime());
            examDTO.setPaperId(exam.getPaperId());
            Paper paper = paperMapper.selectById(exam.getPaperId());
            examDTO.setPaperName(paper.getPaperName());
            examDTO.setTime(paper.getTime());
            //计算结束时间;
            LocalDateTime endTime = exam.getBeginTime()
                    .plusSeconds(paper.getTime());
            examDTO.setEndTime(endTime);
            //计算totalMark
            QueryWrapper<PaperQuestion> paperQuestionQueryWrapper = new QueryWrapper<>();
            paperQuestionQueryWrapper.eq("paper_id", paper.getPaperId());
            List<PaperQuestion> paperQuestionList = paperQuestionMapper.selectList(paperQuestionQueryWrapper);
            int totalMark = 0;
            for(PaperQuestion paperQuestion : paperQuestionList){
                Integer qid = paperQuestion.getQid();
                Question question = questionMapper.selectById(qid);
                totalMark = totalMark + question.getMark();
            }
            examDTO.setTotalMark(totalMark);
            //加入考生名单
            QueryWrapper<ExamUser> examUserQueryWrapper = new QueryWrapper<>();
            examUserQueryWrapper.eq("exam_id", exam.getExamId());
            List<ExamUser> examUserList = examUserMapper.selectList(examUserQueryWrapper);
            List<String> userList = new ArrayList<>();
            for(ExamUser examUser: examUserList){
                User user1 = userMapper.selectById(examUser.getUid());
                userList.add(user1.getUsername());
            }
            examDTO.setUserList(userList);

            examDTOList.add(examDTO);
        }
        if(!examDTOList.isEmpty()){
            examMap.put("error_msg", "success");
            examMap.put("total", total);
            examMap.put("exam_list", examDTOList);
        }else{
            examMap.put("error_msg", "未找到对应考试");
        }
        return examMap;
    }

    //根据ID获取考试详情
    public Map<String, Object> getExamById(Integer exam_id){
        Exam exam = examMapper.selectById(exam_id);
        Map<String, Object> examMap = new HashMap<>();
        if(exam != null){
            examMap.put("examId", exam.getExamId());
            examMap.put("examName", exam.getExamName());
            examMap.put("beginTime", exam.getBeginTime());
            Paper paper = paperMapper.selectById(exam.getPaperId());
            examMap.put("paperName", paper.getPaperName());
            //考生名单
            QueryWrapper<ExamUser> examUserQueryWrapper = new QueryWrapper<>();
            examUserQueryWrapper.eq("exam_id", exam.getExamId());
            List<ExamUser> examUserList = examUserMapper.selectList(examUserQueryWrapper);
            List<String> userList = new ArrayList<>();
            for(ExamUser examUser: examUserList){
                User user1 = userMapper.selectById(examUser.getUid());
                userList.add(user1.getUsername());
            }
            examMap.put("userList", userList);
        }
        else{  //没有搜到对应考试
            examMap.put("error_msg", "未找到对应考试");
        }

        return examMap;
    }

    //实习生根据uid获取其能参加的考试
    public Map<String, Object> getExamByUid(Integer uid, Integer page, Integer pageSize){
        QueryWrapper<ExamUser> examUserQueryWrapper = new QueryWrapper<>();
        examUserQueryWrapper.eq("uid", uid);
        List<ExamUser> examUserList = examUserMapper.selectList(examUserQueryWrapper);
        List<ExamUserDTO> examUserDTOList = new ArrayList<>();
        for(ExamUser examUser: examUserList){
            ExamUserDTO examUserDTO = new ExamUserDTO();
            examUserDTO.setEu_id(examUser.getEuId());
            examUserDTO.setExam_id(examUser.getExamId());
            examUserDTO.setGrade(examUser.getGrade());
            //查询exam_name,begin_time
            Exam exam = examMapper.selectById(examUser.getExamId());
            examUserDTO.setExam_name(exam.getExamName());
            examUserDTO.setBeginTime(exam.getBeginTime());
            //查询time，paper_id,计算end_time，
            Paper paper = paperMapper.selectById(exam.getPaperId());
            examUserDTO.setTime(paper.getTime());
            examUserDTO.setPaper_id(paper.getPaperId());
            LocalDateTime endTime = exam.getBeginTime()
                    .plusSeconds(paper.getTime());
            examUserDTO.setEndTime(endTime);
            //计算考试总分
            QueryWrapper<PaperQuestion> paperQuestionQueryWrapper = new QueryWrapper<>();
            paperQuestionQueryWrapper.eq("paper_id", paper.getPaperId());
            List<PaperQuestion> paperQuestionList = paperQuestionMapper.selectList(paperQuestionQueryWrapper);
            int totalMark = 0;
            for(PaperQuestion paperQuestion : paperQuestionList){
                Integer qid = paperQuestion.getQid();
                Question question = questionMapper.selectById(qid);
                totalMark = totalMark + question.getMark();
            }
            examUserDTO.setTotalMark(totalMark);
            //插入列表
            examUserDTOList.add(examUserDTO);
        }
        //分页
        int totalSize = examUserDTOList.size();
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalSize);
        List<ExamUserDTO> pageList = examUserDTOList.subList(fromIndex, toIndex);
        //返回
        Map<String, Object> examMap = new HashMap<>();
        if(!examUserDTOList.isEmpty()){
            examMap.put("error_msg", "success");
            examMap.put("exam_list", pageList);
            examMap.put("total", totalSize);
        } else{
            examMap.put("error_msg", "考生没有可参与的考试");
        }
        return examMap;
    }

}
