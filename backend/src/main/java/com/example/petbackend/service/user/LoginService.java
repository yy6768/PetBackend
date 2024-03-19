package com.example.petbackend.service.user;

import java.util.Map;

public interface LoginService {
    Map<String,String> getToken(String name, String password);
}
