package com.example.petbackend.service.question;

import java.util.Map;

public interface GetQuestionsService {
    //按照题目搜索分页返回题目列表
    Map<String, Object> getAllQuestionByDescription(Integer page, Integer pageSize, String key);
    //按照病名搜索分页返回题目列表
    Map<String, Object> getAllQuestionByIll(Integer page, Integer pageSize, String key);
    //按照qid返回题目详情
    Map<String, Object> getQuestionByQid(Integer qid);
}
