package com.example.petbackend.controller.paper;

import com.example.petbackend.service.paper.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PaperController {

    @Autowired
    private PaperService paperService;

    //添加试卷
    @PostMapping("/paper/add")
    public Map<String, String> addPaper(@RequestBody Map<String, Object> map){
        Integer uid = Integer.valueOf(map.get("uid").toString());
        String paper_name = (String) map.get("paper_name");
        Integer time = Integer.valueOf(map.get("time").toString());
        //生成当前时间
        Date date = new Date(System.currentTimeMillis());
        // 将题目列表字符串转换为数组
        List<Integer> question_list = (List<Integer>) map.get("question_list");
        return paperService.addPaper(uid, paper_name, time, date, question_list);
    }

    //删除试卷
    @DeleteMapping("/paper/delete")
    public Map<String, String> deletePaper(@RequestParam Map<String, String> map){
        Integer paper_id = Integer.valueOf(map.get("paper_id"));
        return paperService.deletePaper(paper_id);
    }

    @PostMapping("/paper/update")
    public Map<String, String> updatePaper(@RequestBody Map<String, Object> map){
        Integer uid = Integer.valueOf(map.get("uid").toString());
        Integer paper_id = Integer.valueOf(map.get("paper_id").toString());
        String paper_name = (String) map.get("paper_name");
        Integer time = Integer.valueOf(map.get("time").toString());
        // 将题目列表字符串转换为数组
        List<Integer> question_list = (List<Integer>) map.get("question_list");
        return paperService.updatePaper(uid,paper_id,paper_name,time,question_list);

    }



}
