package com.example.petbackend.service.impl.illcase;

import com.alibaba.fastjson.JSONObject;
import com.example.petbackend.mapper.CaseMapper;
import com.example.petbackend.pojo.Illcase;
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
        List<Illcase> illcaseList =caseMapper.sortById();
        return getStringObjectMap(illcaseList);
    }

    @Override
    public Map<String, Object> sortByDoctorCase() {
        List<Illcase> illcaseList =caseMapper.sortByDoctor();
        return getStringObjectMap(illcaseList);
    }

    @Override
    public Map<String, Object> sortByDateCase() {
        List<Illcase> illcaseList =caseMapper.sortByDate();
        return getStringObjectMap(illcaseList);
    }

    @NotNull
    private Map<String, Object> getStringObjectMap(List<Illcase> illcaseList) {
        Map<String, Object> caseMap = new HashMap<>();
        if(illcaseList ==null){
            caseMap.put("error_message", "sort list fail");
        }
        else {
            caseMap.put("error_message", "success");
        }
        caseMap.put("case_list", illcaseList);

        JSONObject obj = new JSONObject(caseMap);
        return obj;
    }
}
