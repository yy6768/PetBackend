package com.example.petbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class IllcaseDTO {
    private Integer cid;
    private String cate_name;
    private String ill_name;
    private String username;
    private LocalDateTime date;
}
