package com.example.petbackend.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.example.petbackend.service.user.manage.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserManageController {
    @Autowired
    private UserManageService userManageService;

    @PostMapping("/user/ban")
    public Map<String,String> banUser(@RequestParam Map<String,String> map){
        int uid = Integer.parseInt(map.get("uid"));
        return userManageService.banUser(uid);
    }

    @PostMapping("/user/unban")
    public Map<String, String> unbanUser(@RequestParam Map<String,String> map){
        int uid = Integer.parseInt(map.get("uid"));
        return userManageService.unbanUser(uid);
    }

    @PostMapping("/user/edit")
    public Map<String, String>editUser(@RequestParam Map<String, String> map) {
        int uid = Integer.parseInt(map.get("uid"));
        String username = map.get("username");
        String password = map.get("password");
        int authority = Integer.parseInt(map.get("authority"));
        return userManageService.editUser(uid, username, password, authority);
    }

    @GetMapping("/user/getall")
    public JSONObject getUserList(@RequestParam Map<String, String> map){
        String key = map.get("key");
        int page = Integer.parseInt(map.get("page"));
        int pageSize = Integer.parseInt(map.get("pageSize"));
        return userManageService.getUserList(key, page, pageSize);
    }



}
