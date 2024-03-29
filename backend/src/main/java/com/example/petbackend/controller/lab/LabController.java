package com.example.petbackend.controller.lab;

import com.example.petbackend.service.lab.LabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
public class LabController {
    @Autowired
    private LabService labService;

    @PostMapping("/lab/add")
    public Map<String,String> addLab(@RequestParam Map<String,String> map){
        String lab_name=map.get("lab_name");
        Double lab_cost= Double.valueOf(map.get("lab_cost"));

        return labService.addLab(lab_name,lab_cost);
    }

    @PostMapping("/lab/update")
    public Map<String,String> updateLab(@RequestParam Map<String,String> map){
        Integer lab_id= Integer.valueOf(map.get("lab_id"));
        Double lab_cost= Double.valueOf(map.get("lab_cost"));

        return labService.updateLab(lab_id,lab_cost);
    }

    @DeleteMapping("/lab/delete")
    public Map<String,String> deleteLab(@RequestParam Map<String,String> map){
        Integer lab_id=Integer.valueOf(map.get("lab_id"));

        return labService.deleteLab(lab_id);
    }

    @GetMapping("/lab/getall")
    public Map<String, Object> getAllLab(){

        return labService.getAllLab();
    }
}
