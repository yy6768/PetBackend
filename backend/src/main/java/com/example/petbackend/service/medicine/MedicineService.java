package com.example.petbackend.service.medicine;

import java.util.Map;

public interface MedicineService {

    Map<String, String> addMedicine(String medicine_name, Double medicine_cost, String description);
    Map<String, String> updateMedicine(Integer medicine_id, Double medicine_cost, String description);
    Map<String, String> deleteMedicine(Integer medicine_id);
    //带搜索值的分页查询
    Map<String, Object> getAllMedicine(Integer page, Integer pageSize, String key);
}
