package com.example.petbackend.service.impl.medicine;

import com.example.petbackend.service.medicine.MedicineService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MedicineServiceImpl implements MedicineService {
    @Override
    public Map<String, String> addMedicine(String medicine_name, Integer medicine_cost){
        return null;
    };
    public Map<String, String> updateMedicine(Integer medicine_id, Integer medicine_cost){
        return null;
    };
    public Map<String, String> deleteMedicine(Integer medicine_id){
        return null;
    };
}
