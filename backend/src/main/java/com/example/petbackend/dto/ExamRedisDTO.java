package com.example.petbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.sql.Timestamp;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamRedisDTO {

    @Id
    private Integer euId;  //exam_user_id主键
    private Integer uid;
    private Integer examId;
    private Timestamp time;  //考试时间
    private Map<Integer, Integer> answerMap; //题号-答案

}
