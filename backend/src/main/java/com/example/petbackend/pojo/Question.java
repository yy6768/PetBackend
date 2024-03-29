package com.example.petbackend.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @TableId(type = IdType.AUTO)
    private Integer qid;
    private Integer cateId;
    private Integer illId;
    private String description;
    private Integer answer;
    private Integer mark;
    private String contentA;
    private String contentB;
    private String contentC;
    private String contentD;
    public Question(Integer cate_id, Integer ill_id, String description, Integer answer, Integer mark,
                     String contentA, String contentB, String contentC, String contentD){
        this.cateId = cate_id;
        this.illId = ill_id;
        this.description = description;
        this.answer = answer;
        this.mark = mark;
        this.contentA = contentA;
        this.contentB = contentB;
        this.contentC = contentC;
        this.contentD = contentD;
    }
}
