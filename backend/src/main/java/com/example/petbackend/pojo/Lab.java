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
    private Integer lab_id;
    private String lab_name;
    private Double lab_cost;
}
