package com.example.petbackend.service.impl.lab;

import com.example.petbackend.service.lab.LabService;
import com.example.petbackend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LabServiceImpl implements LabService {

    @Override
    public Map<String, String> addLab(String lab_name, Double lab_cost) {
        Map<String,String> map=new HashMap<>();
        map.put("error_message","success");
        map.put("lab_name",lab_name);
        map.put("lab_cost", String.valueOf(lab_cost));
        return map;
    }

    @Override
    public Map<String, String> updateLab(Integer lab_id, String lab_name, Double lab_cost) {
        Map<String,String> map=new HashMap<>();
        map.put("error_message","success");
        map.put("lab_id", String.valueOf(lab_id));
        map.put("lab_name",lab_name);
        map.put("lab_cost", String.valueOf(lab_cost));
        return map;
    }

    @Override
    public Map<String, String> deleteLab(Integer lab_id) {
        Map<String,String> map=new HashMap<>();
        map.put("error_message","success");
        map.put("lab_id", String.valueOf(lab_id));
        return map;
    }
}
