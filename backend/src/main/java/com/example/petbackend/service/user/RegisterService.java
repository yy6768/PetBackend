package com.example.petbackend.service.user;

import java.util.Map;

public interface RegisterService {
    public Map<String,String> register(String name,String password,String confirmedPassword);

}
