package com.example.petbackend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paper {
    @TableId(type = IdType.AUTO)
    private Integer paperId;
    private String paperName;
    private Integer time;
    private Integer uid;
    private Date date;
    public Paper(String paperName, Integer time, Integer uid, Date date){
        this.paperName = paperName;
        this.time = time;
        this.uid = uid;
        this.date = date;
    }
}
