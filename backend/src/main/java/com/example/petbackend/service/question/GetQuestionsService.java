package com.example.petbackend.service.question;

import java.util.Map;

public interface GetQuestionsService {
    //带搜索值的分页查询
    Map<String, Object> getAllQuestion(Integer page, Integer pageSize, String key);
}
