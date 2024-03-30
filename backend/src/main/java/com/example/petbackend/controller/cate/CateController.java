package com.example.petbackend.controller.cate;

import com.example.petbackend.service.cate.CateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class CateController {
    @Autowired
    private CateService cateService;
    @GetMapping("/cate/get_all")
    public Map<String, Object> getAllCate(){

        return cateService.getAllCate();
    }
}
