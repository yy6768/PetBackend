package com.example.petbackend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Illcase {
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

    public Illcase(Integer uid, Integer ill_id, Date date){
        this.uid=uid;
        this.illId=ill_id;
        this.date=date;
    }
}
