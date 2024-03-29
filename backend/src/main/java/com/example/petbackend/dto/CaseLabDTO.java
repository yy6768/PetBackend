package com.example.petbackend.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "case_lab")
public class CaseLabDTO {
    private Integer cid;
    private Integer labId;
    private String labResult;
    private String labPhoto;
}
