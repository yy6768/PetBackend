package com.example.petbackend.controller.user;

import com.example.petbackend.service.user.account.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UpdateController {
    @Autowired
    private UpdateService updateService;

    @PostMapping("/user/update_password")
    public Map<String,String> updatePassword(@RequestParam Map<String,String> map){
        int uid = Integer.parseInt(map.get("uid"));
        String old_password = map.get("old_password");
        String new_password = map.get("new_password");

        return updateService.updatePassword(uid, old_password, new_password);
    }

    @PostMapping("/user/update_username")
    public Map<String,String> updateUsername(@RequestParam Map<String,String> map){
        int uid = Integer.parseInt(map.get("uid"));

        String username = map.get("username");
        return updateService.updateUserName(uid, username);
    }

}
