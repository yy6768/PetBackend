package com.example.petbackend.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "case_lab")
public class CaseLab {
    private Integer cid;
    private Integer labId;
    private String labResult;
    private String labPhoto;
}
