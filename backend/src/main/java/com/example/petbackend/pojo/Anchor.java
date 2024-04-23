package com.example.petbackend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Anchor {
    @TableId(type = IdType.AUTO)
    private Integer anchorId;
    private Integer roomId;
    private Float anchorX;
    private Float anchorY;
    private Float anchorZ;
    private String anchorDesc;
    private String imageUrl;
    private String videoUrl;
}
