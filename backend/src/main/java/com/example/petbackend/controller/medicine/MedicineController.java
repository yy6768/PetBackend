package com.example.petbackend.controller.medicine;

import com.example.petbackend.service.medicine.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @PostMapping
    public Map<String, String> addMedicine(@RequestParam Map<String, String> map){
        String medicine_name = map.get("medicine_name");
        Integer medicine_cost= Integer.valueOf(map.get("medicine_cost"));

        return medicineService.addMedicine(medicine_name, medicine_cost);
    }
}
