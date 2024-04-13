package com.example.petbackend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exam {
    @TableId(type = IdType.AUTO)
    private Integer examId;
    private Integer paperId;
    private LocalDateTime beginTime;
    private String examName;
    public Exam(Integer paperId, LocalDateTime beginTime, String examName){
        this.paperId = paperId;
        this.beginTime = beginTime;
        this.examName = examName;
    }
}
