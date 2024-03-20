package com.example.petbackend.service.lab;

import java.util.Map;

public interface LabService {
    Integer addLab(String lab_name,Number lab_cost);

    boolean updateLab(Integer lab_id);

    boolean deleteLab(Integer lab_id);
}
