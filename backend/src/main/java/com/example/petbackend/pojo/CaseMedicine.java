package com.example.petbackend.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "case_medicine")
public class CaseMedicine {
    private Integer cid;
    private Integer medicineId;
}
