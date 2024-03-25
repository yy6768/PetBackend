package com.example.petbackend.service.impl.lab;

import com.example.petbackend.mapper.LabMapper;
import com.example.petbackend.pojo.Lab;
import com.example.petbackend.pojo.Medicine;
import com.example.petbackend.service.lab.LabService;
import com.example.petbackend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LabServiceImpl implements LabService {

    @Autowired
    private LabMapper labMapper;

    @Override
    public Map<String, String> addLab(String lab_name, Double lab_cost) {
        Lab lab = new Lab(lab_name, lab_cost);
        labMapper.insert(lab);
        Map<String,String> labMap=new HashMap<>();
        labMap.put("error_message", "success");
        labMap.put("lab_id", String.valueOf(labMapper.insert(lab)));
        return labMap;
    }

    @Override
    public Map<String, String> updateLab(Integer lab_id, Double lab_cost) {
        Lab lab = labMapper.selectById(lab_id);
        if(lab!=null){
            lab.setLabCost(lab_cost);
            labMapper.updateById(lab);
        }
        Map<String,String> labMap=new HashMap<>();
        if(labMapper.updateById(lab) < 1){
            labMap.put("error_message", "failure");
        }
        else{
            labMap.put("error_message", "success");
        }
        return labMap;
    }

    @Override
    public Map<String, String> deleteLab(Integer lab_id) {
        labMapper.deleteById(lab_id);
        Map<String,String> labMap=new HashMap<>();
        if(labMapper.deleteById(lab_id) < 1){
            labMap.put("error_message", "failure");
        }
        else{
            labMap.put("error_message", "success");
        }
        return labMap;
    }
}
