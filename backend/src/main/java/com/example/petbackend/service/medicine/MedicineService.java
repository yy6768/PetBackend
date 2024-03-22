package com.example.petbackend.service.medicine;

import java.rmi.MarshalledObject;
import java.util.Map;

public interface MedicineService {

    Map<String, String> addMedicine(String medicine_name, Integer medicine_cost);
    Map<String, String> updateMedicine(Integer medicine_id, Integer medicine_cost);
    Map<String, String> deleteMedicine(Integer medicine_id);

}
