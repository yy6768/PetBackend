package com.example.petbackend.service.user.account;

import java.util.Map;

public interface UserManageService {
    Map<String, String> banUser(int uid);

    Map<String, String> unbanUser(int uid);

    Map<String, String> editUser(int uid, String username, String password, int authority);


}
