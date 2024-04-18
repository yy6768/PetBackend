package com.example.petbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamUserDTO {

    private Integer eu_id;
    private String exam_name;
    private Integer exam_id;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private Integer time;   //时限
    private Integer paper_id;  //试卷id
    private Integer grade; //考生成绩
    private Integer totalMark; //考试总分


}
