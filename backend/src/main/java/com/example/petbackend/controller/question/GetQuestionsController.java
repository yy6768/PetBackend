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
     * 按照题目搜索分页返回题目列表
     * @param map
     * @return
     */
    @GetMapping("/getAllQuestionByDescription")
    public Map<String, Object> getAllQuestionByDescription(@RequestParam Map<String, String> map){
        Integer page = Integer.valueOf(map.get("page"));
        Integer pageSize = Integer.valueOf(map.get("pageSize"));
        String key = map.containsKey("key") ? map.get("key") : null;
        Integer cate_id = -1;
        if(map.containsKey("cate_id")){
            if(!map.get("cate_id").isEmpty()){
                cate_id = Integer.valueOf(map.get("cate_id"));
            }
        }
        Integer ill_id = -1;
        if(map.containsKey("ill_id")){
            if(!map.get("ill_id").isEmpty()){
                ill_id = Integer.valueOf(map.get("ill_id"));
            }
        }
        Integer sort = Integer.valueOf(map.get("sort"));
        return getQuestionsService.getAllQuestionByDescription(page, pageSize, key, cate_id, ill_id, sort);
    }

    /**
     * 按照病名搜索分页返回题目列表
     * @param map
     * @return
     */
    @GetMapping("/getAllQuestionByIll")
    public Map<String, Object> getAllQuestionByIll(@RequestParam Map<String, String> map){
        Integer page = Integer.valueOf(map.get("page"));
        Integer pageSize = Integer.valueOf(map.get("pageSize"));
        String key = map.get("key");
        return getQuestionsService.getAllQuestionByIll(page,pageSize,key);
    }

    /**
     * 按照qid返回题目详情
     * @param map
     * @return
     */
    @GetMapping("/getQuestionByQid")
    public Map<String, Object> getQuestionByQid(@RequestParam Map<String, String> map){
        Integer qid = Integer.valueOf(map.get("qid"));
        return getQuestionsService.getQuestionByQid(qid);
    }


}
