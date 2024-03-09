package com.example.petbackend.service.impl;

import com.example.petbackend.service.user.LoginService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {


    @Override
    public Map<String, String> getToken(String name, String password) {
        return null;
    }
}
