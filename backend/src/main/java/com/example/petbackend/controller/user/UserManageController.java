package com.example.petbackend.controller.user;

import com.example.petbackend.service.user.account.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
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

}
