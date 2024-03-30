package com.example.petbackend.controller.question;

import com.example.petbackend.service.question.GetQuestionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class GetQuestionsController {

    @Autowired
    private GetQuestionsService getQuestionsService;

    /**
     * 带搜索值分页获取题目列表
     * @param map
     * @return
     */
    @GetMapping("/getAllQuestion")
    public Map<String, Object> getAllQuestion(@RequestParam Map<String, String> map){
        Integer page = Integer.valueOf(map.get("page"));
        Integer pageSize = Integer.valueOf(map.get("pageSize"));
        String key = map.get("key");
        return getQuestionsService.getAllQuestion(page, pageSize, key);
    }
}
