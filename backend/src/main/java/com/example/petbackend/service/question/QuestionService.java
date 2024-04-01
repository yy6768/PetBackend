package com.example.petbackend.service.question;

import java.util.Map;

public interface QuestionService {
    //添加题目
    Map<String, Object> addQuestion(Integer cate_id, Integer ill_id, String description,
                                    Integer answer, Integer mark, String content_a,
                                    String content_b, String content_c, String content_d);
    //修改题目
    Map<String, String> updateQuestion(Integer qid, Integer cate_id, Integer ill_id, String description,
                                       Integer answer, Integer mark, String content_a,
                                       String content_b, String content_c, String content_d);

    //删除题目
    Map<String, String> deleteQuestion(Integer qid);

}
