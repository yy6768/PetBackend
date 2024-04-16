package com.example.petbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamDTO {

    private Integer examId;
    private String examName;
    private List<String> userList;  //考生名字列表
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private Timestamp time;   //时限
    private Integer totalMark; //总分
    private Integer paperId;  //考试试卷id
    private String paperName; //考试试卷名字


}
