package com.example.petbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamDetailDTO {
    private Integer num;
    private String description;
    private Integer mark;
    private Integer answer;
    private Integer option; //实习生选项
    private String contentA;
    private String contentB;
    private String contentC;
    private String contentD;
}
