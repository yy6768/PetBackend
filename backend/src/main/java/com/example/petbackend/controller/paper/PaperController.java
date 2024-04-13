package com.example.petbackend.controller.paper;

import com.alibaba.fastjson.JSON;
import com.example.petbackend.service.paper.PaperService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PaperController {

    @Autowired
    private PaperService paperService;

    //添加试卷
    @PostMapping("/paper/add")
    public Map<String, String> addPaper(@RequestParam Map<String, String> map){
        Integer uid = Integer.valueOf( map.get("uid"));
        String paper_name =  map.get("paper_name");
        Timestamp time = Timestamp.valueOf(map.get("time"));
        //生成当前时间
        Date date = new Date(System.currentTimeMillis());
        // 将题目列表字符串转换为数组
        String questionListStr = map.get("question_list");
        String[] questionListArray = questionListStr.split(",");
        Integer[] question_list = new Integer[questionListArray.length];
        for (int i = 0; i < questionListArray.length; i++) {
            question_list[i] = Integer.parseInt(questionListArray[i].trim());
        }
        return paperService.addPaper(uid, paper_name, time, date, question_list);
    }

    //删除试卷
    @DeleteMapping("/paper/delete")
    public Map<String, String> deletePaper(@RequestParam Map<String, String> map){
        Integer paper_id = Integer.valueOf(map.get("paper_id"));
        return paperService.deletePaper(paper_id);
    }

    //修改试卷
    @PostMapping("/paper/update")
    public Map<String, String> updatePaper(@RequestParam Map<String, String> map){
        Integer uid = Integer.valueOf(map.get("uid"));
        Integer paper_id = Integer.valueOf(map.get("paper_id"));
        String paper_name = map.get("paper_name");
        Timestamp time = Timestamp.valueOf(map.get("time"));
        // 将题目列表字符串转换为数组
        String questionListStr = map.get("question_list");
        String[] questionListArray = questionListStr.split(",");
        Integer[] question_list = new Integer[questionListArray.length];
        for (int i = 0; i < questionListArray.length; i++) {
            question_list[i] = Integer.parseInt(questionListArray[i].trim());
        }
        return paperService.updatePaper(uid,paper_id,paper_name,time,question_list);

    }



}
