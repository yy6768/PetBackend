package com.example.petbackend.service.user.account;

import java.util.Map;

public interface LoginService {
    Map<String,String> getToken(String name, String password);
}
