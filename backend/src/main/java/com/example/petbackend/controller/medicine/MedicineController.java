package com.example.petbackend.controller.medicine;

import com.example.petbackend.service.medicine.MedicineService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    /**
     * 添加一个药品
     * @param map
     */
    @PostMapping("/medications/add")
    public Map<String, String> addMedicine(@RequestParam Map<String, String> map){
        String medicine_name = map.get("medicine_name");
        Double medicine_cost= Double.valueOf(map.get("medicine_cost"));
        return medicineService.addMedicine(medicine_name, medicine_cost);
    }

    /**
     * 修改一个药品费用
     * @param map
     */
    @PostMapping("/medications/update")
    public Map<String, String> updateMedicine(@RequestParam Map<String, String> map){
        Integer medicine_id = Integer.valueOf(map.get("medicine_id"));
        Double medicine_cost = Double.valueOf(map.get("medicine_cost"));
        return medicineService.updateMedicine(medicine_id, medicine_cost);
    }

    /**
     * 删除一个药品
     * @param map
     */
    @DeleteMapping("/medications/delete")
    public Map<String, String> deleteMedicine(@RequestParam Map<String, String> map){
        Integer medicine_id = Integer.valueOf(map.get("medicine_id"));
        return medicineService.deleteMedicine(medicine_id);
    }


}
