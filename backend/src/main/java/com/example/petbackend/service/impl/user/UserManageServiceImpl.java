package com.example.petbackend.service.impl.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petbackend.mapper.UserMapper;
import com.example.petbackend.pojo.User;
import com.example.petbackend.service.user.manage.UserManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserManageServiceImpl implements UserManageService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> banUser(int uid) {
        Map<String, String> response = new HashMap<>();
        User user = userMapper.selectById(uid);
        if (user == null) {
            response.put("error_message", "用户不存在");
            return response;
        }
        // 禁用用户
        user.setAccess(0);
        int result = userMapper.updateById(user);
        if (result > 0) {
            response.put("error_message", "success");
        } else {
            response.put("error_message", "用户禁用失败");
        }
        return response;
    }

    @Override
    public Map<String, String> unbanUser(int uid) {
        Map<String, String> response = new HashMap<>();
        User user = userMapper.selectById(uid);
        if (user == null) {
            response.put("error_message", "用户不存在");
            return response;
        }
        // 禁用用户
        user.setAccess(1);
        int result = userMapper.updateById(user);
        if (result > 0) {
            response.put("error_message", "success");
        } else {
            response.put("error_message", "用户复用失败");
        }
        return response;
    }

    @Override
    public Map<String, String> editUser(int uid, String username, String password, int authority) {
        Map<String,String> map = new HashMap<>();
        if(username == null) {
            map.put("error_message","用户名不能为空");
            return map;
        }
        if(password == null) {
            map.put("error_message","密码不能为空");
            return map;
        }
        username = username.trim();
        if(username.isEmpty()){
            map.put("error_message","用户名不能为空");
            return map;
        }
        if(password.isEmpty()){
            map.put("error_message","密码不能为空");
            return map;
        }

        if(username.length() > 100){
            map.put("error_message","用户名长度不能大于100");
            return map;
        }

        if(password.length() > 100){
            map.put("error_message","密码长度不能大于100");
            return  map;
        }

        User user = userMapper.selectById(uid);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.ne("uid", uid);
        List<User> users = userMapper.selectList(queryWrapper);
        if(!users.isEmpty()){
            map.put("error_message","用户名已存在");
            return map;
        }

        //特判完毕，开始逻辑处理
        String encodedPassword = passwordEncoder.encode(password);
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setAuthority(authority);
        userMapper.updateById(user);
        map.put("error_message","success");
        return map;
    }

    @Override
    public JSONObject getUserList(String key, int page, int pageSize)   {
        IPage <User> userIPage = new Page<>(page, pageSize);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        key = key.trim();
        if (!key.isEmpty()) {
            userQueryWrapper.like("username", key);
        }
        userIPage = userMapper.selectPage(userIPage, userQueryWrapper);
        List<User> users = userIPage.getRecords();
        JSONObject results = new JSONObject();
        if(users !=null && !users.isEmpty()) { //搜到的药品列表不为空
            results.put("error_message", "success");
            results.put("medicine_list", users);
        } else{
            results.put("error_message", "获取失败");
        }
        return results;
    }


}
