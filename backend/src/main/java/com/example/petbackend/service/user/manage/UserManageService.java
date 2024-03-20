package com.example.petbackend.service.user.manage;

import java.util.Map;

public interface UserManageService {
    Map<String, String> banUser(int uid, int authority);

    Map<String, String> unbanUser(int uid, int authority);

    Map<String, String> editUser(int uid, String username, int authority);

}
