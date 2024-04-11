package com.example.petbackend.service.user.manage;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface UserManageService {

    Map<String, String> banUser(int uid);

    Map<String, String> unbanUser(int uid);

    Map<String, String> editUser(int uid, String username, String password, int authority);

    JSONObject getUserList(String key, Integer authority, int page, int pageSize);
}
