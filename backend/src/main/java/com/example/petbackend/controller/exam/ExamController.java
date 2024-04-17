package com.example.petbackend.controller.exam;

import com.example.petbackend.service.exam.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ExamController {
    @Autowired
    ExamService examService;

    /**
     * 添加一场考试
     * @param map
     * @return
     */
    @PostMapping("/exam/add")
    public Map<String, String> addExam(@RequestBody Map<String, Object> map){
        String exam_name = (String) map.get("exam_name");
        Integer paper_id = Integer.valueOf(map.get("paper_id").toString());
        String timeStr = (String) map.get("begin_time");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime begin_time = LocalDateTime.parse(timeStr,formatter);
        //将user_list键转化为数组
        List<Integer> user_list = (List<Integer>) map.get("user_list");
        return examService.addExam(exam_name, paper_id, begin_time, user_list);
    }

    /**
     * 删除一个考试
     * @param map
     * @return
     */
    @DeleteMapping("/exam/delete")
    public Map<String, String> deleteExam(@RequestParam Map<String, String> map){
        Integer exam_id = Integer.valueOf(map.get("exam_id"));
        return examService.deleteExam(exam_id);
    }

    /**
     * 修改考试
     * @param map
     * @return
     */
    @PostMapping("/exam/update")
    public Map<String, String> updateExam(@RequestBody Map<String, Object> map){
        Integer exam_id = Integer.valueOf(map.get("exam_id").toString());
        String exam_name = (String) map.get("exam_name");
        Integer paper_id = Integer.valueOf(map.get("paper_id").toString());
        String timeStr = (String) map.get("begin_time");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime begin_time = LocalDateTime.parse(timeStr,formatter);
        //将user_list键转化为数组
        List<Integer> user_list = (List<Integer>) map.get("user_list");
        return examService.updateExam(exam_id, exam_name, paper_id, begin_time, user_list);
    }
}
