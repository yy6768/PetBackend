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
@TableName(value = "exam_user")
public class ExamUser {
    @TableId(type = IdType.AUTO)
    private Integer euId;
    private Integer examId;
    private Integer uid;
    public ExamUser(Integer examId, Integer uid){
        this.examId = examId;
        this.uid = uid;
    }
}
