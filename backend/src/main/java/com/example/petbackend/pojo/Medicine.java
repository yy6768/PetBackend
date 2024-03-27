package com.example.petbackend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicine {
    @TableId(type = IdType.AUTO)
    private Integer medicineId;
    private String medicineName;
    private Double medicineCost;
    public Medicine(String medicine_name, Double medicine_cost){
        this.medicineName=medicine_name;
        this.medicineCost=medicine_cost;
    }
}
