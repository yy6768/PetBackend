package com.example.petbackend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @TableId(type = IdType.AUTO)
    private Integer roomId;
    private String  roomName;
    private String  roomNum;
    private String  description;
}
