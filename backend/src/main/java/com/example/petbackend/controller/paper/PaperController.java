package com.example.petbackend.controller.paper;

import com.example.petbackend.service.paper.PaperService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PaperController {

    @Autowired
    private PaperService paperService;

    //添加试卷
    @SneakyThrows
    @PostMapping("/paper/add")
    public Map<String, String> addPaper(@RequestParam Map<String, String> map) throws ParseException {
        Integer uid = Integer.valueOf(map.get("uid"));
        String paper_name = map.get("paper_name");
        Timestamp time = Timestamp.valueOf(map.get("time"));
        Date date = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(map.get("date"));
        // 将question_list字符串转换为数组
        String questionListStr = map.get("question_list");
        ObjectMapper objectMapper = new ObjectMapper();
        Integer[] question_list = objectMapper.readValue(questionListStr, Integer[].class);
        return paperService.addPaper(uid, paper_name, time, date, question_list);
    }

    //删除试卷
    @DeleteMapping("/paper/delete")
    public Map<String, String> deletePaper(@RequestParam Map<String, String> map){
        Integer paper_id = Integer.valueOf(map.get("paper_id"));
        return paperService.deletePaper(paper_id);
    }

    //修改试卷
    @SneakyThrows
    @PostMapping("/paper/update")
    public Map<String, String> updatePaper(@RequestParam Map<String, String> map){
        Integer uid = Integer.valueOf(map.get("uid"));
        Integer paper_id = Integer.valueOf(map.get("paper_id"));
        String paper_name = map.get("paper_name");
        Timestamp time = Timestamp.valueOf(map.get("time"));
        // 将question_list字符串转换为数组
        String questionListStr = map.get("question_list");
        ObjectMapper objectMapper = new ObjectMapper();
        Integer[] question_list = objectMapper.readValue(questionListStr, Integer[].class);
        return paperService.updatePaper(uid, paper_id,paper_name, time, question_list);

    }



}
