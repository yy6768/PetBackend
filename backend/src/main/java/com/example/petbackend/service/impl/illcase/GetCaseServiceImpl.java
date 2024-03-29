package com.example.petbackend.service.impl.illcase;

import com.alibaba.fastjson.JSONObject;
import com.example.petbackend.mapper.CaseMapper;
import com.example.petbackend.pojo.Illcase;
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
        List<Illcase> illcaseList =caseMapper.selectByCate(cate_name);
        return getStringObjectMap(illcaseList);
    }

    @Override
    public Map<String, Object> getByIllCase(String ill_name) {
        List<Illcase> illcaseList =caseMapper.selectByIll(ill_name);
        return getStringObjectMap(illcaseList);
    }

    @Override
    public Map<String, Object> getByDateCase(Date date) {
        List<Illcase> illcaseList =caseMapper.selectByDate(date);
        return getStringObjectMap(illcaseList);
    }

    @NotNull
    private Map<String, Object> getStringObjectMap(List<Illcase> illcaseList) {
        Map<String, Object> caseMap = new HashMap<>();
        if(illcaseList ==null){
            caseMap.put("error_message", "get list fail");
        }
        else {
            caseMap.put("error_message", "success");
        }
        caseMap.put("case_list", illcaseList);

        JSONObject obj = new JSONObject(caseMap);
        return obj;
    }
}
