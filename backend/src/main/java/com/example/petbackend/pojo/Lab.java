package com.example.petbackend.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lab {
    @TableId(type = IdType.AUTO)
    private Integer labId;
    private String labName;
    private Double labCost;

    public Lab(String lab_name, Double lab_cost){
        this.labName=lab_name;
        this.labCost=lab_cost;
    }
}
