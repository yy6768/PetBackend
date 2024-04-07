package com.example.petbackend.service.impl.illcase;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petbackend.mapper.CaseMapper;
import com.example.petbackend.pojo.Illcase;
import com.example.petbackend.service.illcase.SortCaseService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SortCaseServiceImpl implements SortCaseService {

    @Autowired
    private CaseMapper caseMapper;
    @Override
    public Map<String, Object> sortByIdCase(Integer page, Integer pageSize) {
        List<Illcase> illcaseList =caseMapper.sortById();
        return getStringObjectMap(page, pageSize, illcaseList);
    }

    @Override
    public Map<String, Object> sortByDoctorCase(Integer page, Integer pageSize) {
        List<Illcase> illcaseList =caseMapper.sortByDoctor();
        return getStringObjectMap(page, pageSize, illcaseList);
    }

    @Override
    public Map<String, Object> sortByDateCase(Integer page, Integer pageSize) {
        List<Illcase> illcaseList =caseMapper.sortByDate();
        return getStringObjectMap(page, pageSize, illcaseList);
    }

    @NotNull
    private Map<String, Object> getStringObjectMap(Integer page, Integer pageSize, List<Illcase> illcaseList) {
        List<Integer> caseIdList = new ArrayList<>();
        for (Illcase illcase : illcaseList) {
            caseIdList.add(illcase.getCid());
        }
        IPage<Illcase> casePage = new Page<>(page, pageSize);
        casePage = caseMapper.selectPage(casePage, Wrappers.<Illcase>lambdaQuery()
                .in(Illcase::getCid, caseIdList));
        List<Illcase> illcases =casePage.getRecords();
        Map<String, Object> caseMap = new HashMap<>();
        if(illcases !=null && !illcases.isEmpty()) {
            caseMap.put("error_message", "success");
            caseMap.put("case_list", illcases);
        } else{
            caseMap.put("error_message", "未找到对应case");
        }

        JSONObject obj = new JSONObject(caseMap);
        return obj;
    }

}
