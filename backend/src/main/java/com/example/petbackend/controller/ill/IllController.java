package com.example.petbackend.controller.ill;

import com.example.petbackend.service.ill.IllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class IllController {
    @Autowired
    private IllService illService;
    @GetMapping("/ill/get_all_in_cate")
    public Map<String,Object> getAllInCateIll(@RequestParam Map<String,String> map){
        String cate_id=map.get("cate_id");

        return illService.getAllInCateIll(cate_id);
    }

}
