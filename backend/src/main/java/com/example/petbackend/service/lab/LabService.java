package com.example.petbackend.service.lab;

import java.util.Map;

public interface LabService {
    Map<String,String> addLab(String lab_name,Double lab_cost, String description);

    Map<String,String> updateLab(Integer lab_id,Double lab_cost, String description);

    Map<String,String> deleteLab(Integer lab_id);

    Map<String,Object> getAllLab(Integer page, Integer pageSize,String search);
}
