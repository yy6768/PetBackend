package com.example.petbackend.service.user.manage;

import com.example.petbackend.pojo.User;

import java.util.List;

public interface GetUsersService {
    List<User> getAllUsers(int authority);
    
}
