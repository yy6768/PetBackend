package com.example.petbackend.service.paper;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PaperService {
    //添加试卷
    Map<String, String> addPaper(Integer uid, String paper_name, Integer time, Date date, List<Integer> question_list);
    //删除试卷
    Map<String, String> deletePaper(Integer paper_id);
    //修改试卷
    Map<String, String> updatePaper(Integer uid, Integer paper_id, String paper_name, Integer time, List<Integer> question_list);
}
