package com.example.petbackend.controller.paper;

import com.example.petbackend.service.paper.GetPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class GetPaperController {
    @Autowired
    GetPaperService getPaperService;

    /**
     * 根据关键字搜索分页获取所有试卷
     * @param map
     * @return
     */
    @GetMapping("/paper/getAll")
    public Map<String, Object> getAllPaper(@RequestParam Map<String, String> map){

        Integer page = Integer.valueOf(map.get("page"));
        Integer pageSize = Integer.valueOf(map.get("pageSize"));
        String key = map.containsKey("key") ? map.get("key") : null;
        Integer type = Integer.valueOf(map.get("type"));
        return getPaperService.getAllPaper(page,pageSize,key,type);
    }

    /**
     * 根据ID获取试卷
     * @param map
     * @return
     */
    @GetMapping("/paper/getById")
    public Map<String, Object> getPaperById(@RequestParam Map<String, String> map){
        Integer paper_id = Integer.valueOf(map.get("paper_id"));
        return getPaperService.getPaperById(paper_id);
    }
}
