package com.example.petbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IllcaseSearchDTO {
    private Integer cid;
    private Integer username;
    private Integer illName;
    private Date date;
    private String basicSituation;
    private String photo;
    private String result;
    private String therapy;
    private String surgeryVideo;
}
