package com.example.petbackend.service.impl.illcase;

import com.example.petbackend.dto.CaseLabDTO;
import com.example.petbackend.dto.CaseMedicineDTO;
import com.example.petbackend.mapper.CaseLabMapper;
import com.example.petbackend.mapper.CaseMapper;
import com.example.petbackend.mapper.CaseMedicineMapper;
import com.example.petbackend.pojo.Illcase;
import com.example.petbackend.service.illcase.UpdateCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateCaseServiceImpl implements UpdateCaseService {
    @Autowired
    private CaseMapper caseMapper;
    @Autowired
    private CaseLabMapper caseLabMapper;
    @Autowired
    private CaseMedicineMapper caseMedicineMapper;
    @Override
    public Map<String, String> addLabCase(Integer cid, Integer lab_id, String lab_result, String lab_photo) {
        CaseLabDTO caseLabDTO=new CaseLabDTO(cid,lab_id,lab_result,lab_photo);
        Map<String,String> caseLabMap=new HashMap<>();
        caseLabMapper.insert(caseLabDTO);
        caseLabMap.put("error_message", "success");
        caseLabMap.put("cid", String.valueOf(caseLabDTO.getCid()));
        return caseLabMap;
    }

    @Override
    public Map<String, String> deleteLabCase(Integer cid, Integer lab_id) {
        Map<String,String> caseLabMap=new HashMap<>();
        if(caseLabMapper.deleteById(cid,lab_id) < 1){
            caseLabMap.put("error_message", "delete fail");
        }
        else{
            caseLabMap.put("error_message", "success");
        }
        return caseLabMap;
    }

    @Override
    public Map<String, String> addMedicineCase(Integer cid, Integer medicine_id) {
        CaseMedicineDTO caseMedicineDTO=new CaseMedicineDTO(cid,medicine_id);
        Map<String,String> caseMedicineMap=new HashMap<>();
        caseMedicineMapper.insert(caseMedicineDTO);
        caseMedicineMap.put("error_message", "success");
        caseMedicineMap.put("cid", String.valueOf(caseMedicineDTO.getCid()));
        return caseMedicineMap;
    }

    @Override
    public Map<String, String> deleteMedicineCase(Integer cid, Integer medicine_id) {
        Map<String,String> caseMedicineMap=new HashMap<>();
        if(caseMedicineMapper.deleteById(cid,medicine_id) < 1){
            caseMedicineMap.put("error_message", "delete fail");
        }
        else{
            caseMedicineMap.put("error_message", "success");
        }
        return caseMedicineMap;
    }
}
