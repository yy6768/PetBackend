package com.example.petbackend.controller.question;

import com.example.petbackend.service.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 添加一个题目
     * @param map
     * @return
     */
    @PostMapping("/createQuestion")
    public Map<String, Object> addQuestion(@RequestParam Map<String, String> map){
        Integer cate_id = Integer.valueOf(map.get("cate_id"));
        Integer ill_id = Integer.valueOf(map.get("ill_id"));
        String description = map.get("description");
        Integer answer = Integer.valueOf(map.get("answer"));
        Integer mark = Integer.valueOf(map.get("mark"));
        String content_a = map.get("content_a");
        String content_b = map.get("content_b");
        String content_c = map.get("content_c");
        String content_d = map.get("content_d");
        return questionService.addQuestion(cate_id, ill_id, description, answer, mark, content_a,
                content_b, content_c, content_d);
    }

    /**
     * 修改题目
     * @param map
     * @return
     */
    @PostMapping("/updateQuestion")
    public Map<String, String> updateQuestion(@RequestParam Map<String, String> map){
        Integer qid = Integer.valueOf(map.get("qid"));
        Integer cate_id = Integer.valueOf(map.get("cate_id"));
        Integer ill_id = Integer.valueOf(map.get("ill_id"));
        String description = map.get("description");
        Integer answer = Integer.valueOf(map.get("answer"));
        Integer mark = Integer.valueOf(map.get("mark"));
        String content_a = map.get("content_a");
        String content_b = map.get("content_b");
        String content_c = map.get("content_c");
        String content_d = map.get("content_d");
        return questionService.updateQuestion(qid, cate_id, ill_id, description, answer, mark,
                content_a, content_b, content_c, content_d);

    }

    /**
     * 删除题目
     * @param map
     * @return
     */
    @DeleteMapping("/deleteQuestion")
    public Map<String,String> deleteQuestion(@RequestParam Map<String, String> map){
        Integer qid = Integer.valueOf(map.get("qid"));
        return questionService.deleteQuestion(qid);
    }




}
