package com.example.petbackend.service.paper;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;

public interface PaperService {
    //添加试卷
    Map<String, String> addPaper(Integer uid, String paper_name, Timestamp time, Date date, Integer[] question_list);
    //删除试卷
    Map<String, String> deletePaper(Integer paper_id);
    //修改试卷
    Map<String, String> updatePaper(Integer uid, Integer paper_id, String paper_name, Timestamp time, Integer[] question_list);
}
