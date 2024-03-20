package com.example.petbackend.service.user.account;

import java.util.Map;

public interface UpdateService {
    Map<String, String> updateUserName(int uid, String username);
    Map<String, String> updatePassword(int uid, String oldPassword, String newPassword);
}
