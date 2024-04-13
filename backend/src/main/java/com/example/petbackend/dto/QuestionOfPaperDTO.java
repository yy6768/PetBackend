package com.example.petbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionOfPaperDTO {
    private Integer num;
    private String description;
    private Integer mark;
    private String contentA;
    private String contentB;
    private String contentC;
    private String contentD;
}
