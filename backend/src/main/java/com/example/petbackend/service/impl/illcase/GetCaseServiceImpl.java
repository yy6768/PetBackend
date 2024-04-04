package com.example.petbackend.service.impl.illcase;

import com.alibaba.fastjson.JSONObject;
import com.example.petbackend.mapper.CaseMapper;
import com.example.petbackend.pojo.IllCase;
import com.example.petbackend.service.illcase.GetCaseService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GetCaseServiceImpl implements GetCaseService {
    @Autowired
    private CaseMapper caseMapper;
    @Override
    public Map<String, Object> getByCateCase(String cate_name) {
        List<IllCase> illCaseList =caseMapper.selectByCate(cate_name);
        return getStringObjectMap(illCaseList);
    }

    @Override
    public Map<String, Object> getByIllCase(String ill_name) {
        List<IllCase> illCaseList =caseMapper.selectByIll(ill_name);
        return getStringObjectMap(illCaseList);
    }

    @Override
    public Map<String, Object> getByDateCase(Date date) {
        List<IllCase> illCaseList =caseMapper.selectByDate(date);
        return getStringObjectMap(illCaseList);
    }

    @NotNull
    private Map<String, Object> getStringObjectMap(List<IllCase> illCaseList) {
        Map<String, Object> caseMap = new HashMap<>();
        if(illCaseList ==null){
            caseMap.put("error_message", "get list fail");
        }
        else {
            caseMap.put("error_message", "success");
        }
        caseMap.put("case_list", illCaseList);

        JSONObject obj = new JSONObject(caseMap);
        return obj;
    }
}
