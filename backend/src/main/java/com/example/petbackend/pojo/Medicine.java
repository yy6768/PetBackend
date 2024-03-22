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
    private Integer medicine_id;
    private String medicine_name;
    private Double medicine_cost;
    public Medicine(String medicine_name, Double medicine_cost){
        this.medicine_name=medicine_name;
        this.medicine_cost=medicine_cost;
    }
}
