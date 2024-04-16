package com.example.petbackend.service.exam;

import java.util.Map;

public interface GetExamService {

    //根据考试名搜索分页获取所有考试
    Map<String, Object> getAllExamByName(Integer page, Integer pageSize, String key);
    //根据ID获取考试详情
    Map<String, Object> getExamById(Integer exam_id);
}
