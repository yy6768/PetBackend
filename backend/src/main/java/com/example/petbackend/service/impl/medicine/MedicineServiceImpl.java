package com.example.petbackend.service.impl.medicine;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petbackend.mapper.CaseMapper;
import com.example.petbackend.mapper.CaseMedicineMapper;
import com.example.petbackend.mapper.MedicineMapper;
import com.example.petbackend.pojo.CaseMedicine;
import com.example.petbackend.pojo.Medicine;
import com.example.petbackend.service.medicine.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    MedicineMapper medicineMapper;
    @Autowired
    private CaseMedicineMapper caseMedicineMapper;

    //添加药品
    @Override
    public Map<String, String> addMedicine(String medicine_name, Double medicine_cost, String description){
        Medicine medicine = new Medicine(null, medicine_name, medicine_cost, description);
        medicineMapper.insert(medicine);
        Map<String,String> medicineMap=new HashMap<>();
        medicineMap.put("error_message", "success");
        medicineMap.put("medicine_id", String.valueOf(medicine.getMedicineId()));
        return medicineMap;
    }

    //修改药品费用
    @Override
    public Map<String, String> updateMedicine(Integer medicine_id, Double medicine_cost, String description) {
        Medicine medicine = medicineMapper.selectById(medicine_id);
        int res = 0;
        if (medicine != null) {
            medicine.setMedicineCost(medicine_cost);
            medicine.setDescription(description);
            res = medicineMapper.updateById(medicine);
        }
        Map<String, String> medicineMap = new HashMap<>();
        String msg;
        if (res < 1) {
            msg = "数据库中未找到对应药品，修改失败";
        } else {
            msg = "success";
        }
        medicineMap.put("error_message", msg);
        return medicineMap;
    }

    //删除药品
    @Override
    public Map<String, String> deleteMedicine(Integer medicine_id){

        int res= caseMedicineMapper.deleteByMedId(medicine_id);
        int result=medicineMapper.deleteById(medicine_id);

        Map<String,String> medicineMap=new HashMap<>();
        if(result < 1||res<1){
            medicineMap.put("error_message", "数据库中未找到对应药品，删除失败");
        }
        else{
            medicineMap.put("error_message", "success");
        }
        return medicineMap;
    }


    //带搜索值分页获取药品列表
    @Override
    public  Map<String, Object> getAllMedicine(Integer page, Integer pageSize, String key){
        IPage<Medicine> medicinePage = new Page<>(page, pageSize);
        QueryWrapper<Medicine> medicineQueryWrapper = new QueryWrapper<>();
        if(key != null && !key.isEmpty()) medicineQueryWrapper.like("medicine_name", key);
        medicinePage = medicineMapper.selectPage(medicinePage, medicineQueryWrapper);
        List<Medicine> medicineList = medicinePage.getRecords();
        long total = medicineMapper.selectCount(medicineQueryWrapper);
        Map<String, Object> medicineMap = new HashMap<>();
        if(medicineList !=null && !medicineList.isEmpty()) { //搜到的药品列表不为空
            medicineMap.put("error_message", "success");
            medicineMap.put("medicine_list", medicineList);
            medicineMap.put("total", total); // 添加记录总数
        } else{
            medicineMap.put("error_message", "未找到对应药品");
        }
        return new JSONObject(medicineMap);
    }


}
