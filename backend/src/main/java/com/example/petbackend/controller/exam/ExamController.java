package com.example.petbackend.controller.exam;

import com.example.petbackend.service.exam.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public Map<String, String> addExam(@RequestParam Map<String, String> map){
        String exam_name = map.get("exam_name");
        Integer paper_id = Integer.valueOf(map.get("paper_id"));
        String timeStr = map.get("begin_time");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime begin_time = LocalDateTime.parse(timeStr,formatter);
        //将user_list键转化为数组
        String userListStr = map.get("user_list");
        String[] userListArray = userListStr.split(",");
        Integer[] user_list = new Integer[userListArray.length];
        for(int i = 0; i < userListArray.length; i++){
            user_list[i] = Integer.parseInt(userListArray[i].trim());
        }
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
    public Map<String, String> updateExam(@RequestParam Map<String, String> map){
        Integer exam_id = Integer.valueOf(map.get("exam_id"));
        String exam_name = map.get("exam_name");
        Integer paper_id = Integer.valueOf(map.get("paper_id"));
        String timeStr = map.get("begin_time");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime begin_time = LocalDateTime.parse(timeStr,formatter);
        //将user_list键转化为数组
        String userListStr = map.get("user_list");
        String[] userListArray = userListStr.split(",");
        Integer[] user_list = new Integer[userListArray.length];
        for(int i = 0; i < userListArray.length; i++){
            user_list[i] = Integer.parseInt(userListArray[i].trim());
        }
        return examService.updateExam(exam_id, exam_name, paper_id, begin_time, user_list);
    }
}
