package com.example.petbackend.controller.exam;

import com.example.petbackend.service.exam.GetExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class GetExamController {

    @Autowired
    GetExamService getExamService;

    /**
     * 根据考试名搜索分页获取所有考试
     * @param map
     * @return
     */
    @GetMapping ("/exam/getAll")
    public Map<String, Object> getAllExamByName(@RequestParam Map<String, String> map){
        Integer page = Integer.valueOf(map.get("page"));
        Integer pageSize = Integer.valueOf(map.get("pageSize"));
        String key = map.containsKey("key") ? map.get("key") : null;
        return getExamService.getAllExamByName(page, pageSize, key);
    }

    /**
     * 根据ID获取考试详情
     * @param map
     * @return
     */
    @GetMapping("/exam/getById")
    public Map<String, Object> getExamById(@RequestParam Map<String, String> map){
        Integer exam_id = Integer.valueOf(map.get("exam_id"));
        return getExamService.getExamById(exam_id);
    }


}