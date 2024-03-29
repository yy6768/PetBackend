package com.example.petbackend.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "case_medicine")
public class CaseMedicineDTO {
    private Integer cid;
    private Integer medicineId;
}
