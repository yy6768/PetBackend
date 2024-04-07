package com.example.petbackend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "paper_question")
public class PaperQuestion {
    @TableId(type = IdType.AUTO)
    private Integer pq_id;
    private Integer paper_id;
    private Integer qid;
    private Integer num;
    public PaperQuestion(Integer paper_id, Integer qid, Integer num){
        this.paper_id = paper_id;
        this.qid = qid;
        this.num = num;
    }

}
