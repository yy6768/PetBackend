package com.example.petbackend.service.question;


import java.util.Map;

public interface SortQuestionService {
    Map<String, Object> sortQuestionByIll(String key, Integer page, Integer pageSize);
}
