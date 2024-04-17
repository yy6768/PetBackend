package com.example.petbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class LabDTO {
    private String lab_name;
    private Double lab_cost;
    private String labResult;
    private String labPhoto;
}
