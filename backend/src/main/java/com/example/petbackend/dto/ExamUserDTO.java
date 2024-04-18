package com.example.petbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;
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
    private Timestamp time;   //时限
    private Integer grade; //考生成绩
    private Integer totalMark; //考试总分


}
