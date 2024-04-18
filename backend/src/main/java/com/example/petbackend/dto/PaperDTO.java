package com.example.petbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaperDTO {
    private Integer paperId;
    private String paperName;
    private Integer time;
    private Integer uid;
    private String username; //用户名
    private Date date;
    private Integer totalMark; //总分
}
