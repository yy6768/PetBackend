package com.example.petbackend.pojo;

import com.example.petbackend.mapper.UserMapper;
import com.example.petbackend.service.user.manage.UserManageService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IllcaseDoc {
    private Integer cid;
    private String username;
    private String cateName;
    private String illName;
    private String date;
    private String basicSituation;
    private String result;
    private String therapy;

}
