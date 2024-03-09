package com.example.petbackend.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hospital/")
public class IndexController {
    @RequestMapping("index/")
    public String index() {
        return "hospital/index.html";
    }
}
