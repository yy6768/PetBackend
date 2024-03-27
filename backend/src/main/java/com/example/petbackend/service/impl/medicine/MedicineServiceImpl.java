package com.example.petbackend.service.impl.medicine;

import com.example.petbackend.mapper.MedicineMapper;
import com.example.petbackend.pojo.Medicine;
import com.example.petbackend.service.medicine.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    MedicineMapper medicineMapper;

    //添加药品
    @Override
    public Map<String, String> addMedicine(String medicine_name, Double medicine_cost){
        Medicine medicine = new Medicine(medicine_name, medicine_cost);
        medicineMapper.insert(medicine);
        Map<String,String> medicineMap=new HashMap<>();
        medicineMap.put("error_message", "success");
        medicineMap.put("medicine_id", String.valueOf(medicine.getMedicineId()));
        return medicineMap;
    };

    //修改药品费用
    @Override
    public Map<String, String> updateMedicine(Integer medicine_id, Double medicine_cost) {
        Medicine medicine = medicineMapper.selectById(medicine_id);
        int res = 0;
        if (medicine != null) {
            medicine.setMedicineCost(medicine_cost);
            res = medicineMapper.updateById(medicine);
        }
        Map<String, String> medicineMap = new HashMap<>();
        String msg;
        if (res < 1) {
            msg = "updatefail";
        } else {
            msg = "success";
        }
        medicineMap.put("error_msg", msg);
        return medicineMap;
    };

    //删除药品
    @Override
    public Map<String, String> deleteMedicine(Integer medicine_id){
        int result = medicineMapper.deleteById(medicine_id);
        Map<String,String> medicineMap=new HashMap<>();
        if(result < 1){
            medicineMap.put("error_message", "deletefail");
        }
        else{
            medicineMap.put("error_message", "success");
        }
        return medicineMap;
    };
}
