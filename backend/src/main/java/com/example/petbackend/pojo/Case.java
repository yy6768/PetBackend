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
public class Case {
    @TableId(type = IdType.AUTO)
    private Integer cid;
    private Integer uid;
    private Integer illId;
    private Date date;
    private String basicSituation;
    private String photo;
    private String result;
    private String therapy;
    private String surgeryVideo;

    public Case(Integer uid,Integer ill_id,Date date,
                String basic_situation,String photo,String result,
                String therapy,String surgery_video){
        this.uid=uid;
        this.illId=ill_id;
        this.date=date;
        this.basicSituation=basic_situation;
        this.photo=photo;
        this.result=result;
        this.therapy=therapy;
        this.surgeryVideo=surgery_video;
    }
}
