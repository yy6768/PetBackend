package com.example.petbackend.service.impl.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.petbackend.mapper.UserMapper;
import com.example.petbackend.pojo.User;
import com.example.petbackend.service.user.account.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UpdateServiceImpl implements UpdateService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Map<String, String> updateUserName(int uid, String username) {
        Map<String,String> map = new HashMap<>();
        if(username == null) {
            map.put("error_message", "新用户名不能为空");
            return map;
        }
        username = username.trim();
        if(username.isEmpty()){
            map.put("error_message","新用户名不能为空");
            return map;
        }

        if(username.length() > 100){
            map.put("error_message","新用户名长度不能大于100");
            return map;
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        List<User> users = userMapper.selectList(queryWrapper);
        if(!users.isEmpty()){
            map.put("error_message","用户名已存在，无法修改");
            return map;
        }
        User user = userMapper.selectById(uid);
        if (user == null) {
            map.put("error_message", "用户不存在");
            return map;
        }
        //特判完毕，开始逻辑处理
        user.setUsername(username);
        userMapper.updateById(user);
        map.put("error_message","success");
        return map;
    }

    @Override
    public Map<String, String> updatePassword(int uid, String oldPassword, String newPassword) {
        Map<String,String> map = new HashMap<>();

        User user = userMapper.selectById(uid);
        if (user == null) {
            map.put("error_message", "用户不存在");
            return map;
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            map.put("error_message", "用户不存在");
            return map;
        }

        String encodedNewPassword = passwordEncoder.encode(newPassword);

        user.setPassword(encodedNewPassword);
        userMapper.updateById(user);
        //特判完毕，开始逻辑处理
        map.put("error_message","success");
        return map;
    }
}
