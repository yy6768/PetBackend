package com.example.petbackend.service.impl.illcase;

import com.alibaba.fastjson.JSONObject;
import com.example.petbackend.mapper.CaseMapper;
import com.example.petbackend.pojo.IllCase;
import com.example.petbackend.service.illcase.SortCaseService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SortCaseServiceImpl implements SortCaseService {

    @Autowired
    private CaseMapper caseMapper;
    @Override
    public Map<String, Object> sortByIdCase() {
        List<IllCase> illCaseList =caseMapper.sortById();
        return getStringObjectMap(illCaseList);
    }

    @Override
    public Map<String, Object> sortByDoctorCase() {
        List<IllCase> illCaseList =caseMapper.sortByDoctor();
        return getStringObjectMap(illCaseList);
    }

    @Override
    public Map<String, Object> sortByDateCase() {
        List<IllCase> illCaseList =caseMapper.sortByDate();
        return getStringObjectMap(illCaseList);
    }

    @NotNull
    private Map<String, Object> getStringObjectMap(List<IllCase> illCaseList) {
        Map<String, Object> caseMap = new HashMap<>();
        if(illCaseList ==null){
            caseMap.put("error_message", "sort list fail");
        }
        else {
            caseMap.put("error_message", "success");
        }
        caseMap.put("case_list", illCaseList);

        JSONObject obj = new JSONObject(caseMap);
        return obj;
    }
}
