package com.example.petbackend.service.exam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ExamService {
    //添加一个考试
    Map<String, String> addExam(String exam_name, Integer paper_id, LocalDateTime begin_time, List<Integer> user_list);
    //删除一个考试
    Map<String, String> deleteExam(Integer exam_id);
    //修改一个考试
    Map<String, String> updateExam(Integer exam_id, String exam_name,
                                   Integer paper_id, LocalDateTime begin_time, List<Integer> user_list);
}
