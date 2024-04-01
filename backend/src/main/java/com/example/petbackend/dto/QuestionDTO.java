package com.example.petbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private int qid;
    private Integer cateID;
    private String cateName;
    private Integer illId;
    private String illName;
    private String description;
    private Integer answer;
    private Integer mark;
    private String contentA;
    private String contentB;
    private String contentC;
    private String contentD;
}
