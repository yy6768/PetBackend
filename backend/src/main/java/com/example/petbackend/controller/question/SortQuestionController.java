package com.example.petbackend.controller.question;

import com.example.petbackend.service.question.SortQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class SortQuestionController {
    @Autowired
    SortQuestionService sortQuestionService;

    /**
     * 根据病名排序分页获取题目列表
     * @param map
     * @return
     */
    @GetMapping("/sortQuestionByIll")
    public Map<String, Object> sortQuestionByIll(@RequestParam Map<String, String> map){
        String key = map.containsKey("key") ? map.get("key") : null;
        Integer page = Integer.valueOf(map.get("page"));
        Integer pageSize = Integer.valueOf(map.get("pageSize"));
        return sortQuestionService.sortQuestionByIll(key,page,pageSize);
    }

}
