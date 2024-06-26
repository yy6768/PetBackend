package com.example.petbackend.service.impl.illcase;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petbackend.dto.QuestionDTO;
import com.example.petbackend.mapper.CaseMapper;
import com.example.petbackend.mapper.CateMapper;
import com.example.petbackend.mapper.IllMapper;
import com.example.petbackend.pojo.Cate;
import com.example.petbackend.pojo.Ill;
import com.example.petbackend.pojo.Illcase;
import com.example.petbackend.pojo.Question;
import com.example.petbackend.service.illcase.GetCaseService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDateTime;

@Service
public class GetCaseServiceImpl implements GetCaseService {
    @Autowired
    private CaseMapper caseMapper;
    @Autowired
    private IllMapper illMapper;
    @Autowired
    private CateMapper cateMapper;
    @Override
    public Map<String, Object> getByCateCase(Integer page, Integer pageSize,String cate_name) {
        List<Ill> illList = illMapper.selectByCate(cate_name);
        return getStringObjectMap(page, pageSize, illList);
    }

    @Override
    public Map<String, Object> getByIllCase(Integer page, Integer pageSize,String ill_name) {
        List<Ill> illList = illMapper.selectByName(ill_name);
        return getStringObjectMap(page, pageSize, illList);
    }

    @Override
    public Map<String, Object> getByDateCase(Integer page, Integer pageSize, LocalDateTime start,LocalDateTime end) {
        List<Illcase> illcaseList=caseMapper.selectRangeByDate(start,end);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String formattedDateTime = date.format(formatter);

        int totalSize = illcaseList.size();
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, totalSize);
        List<Illcase> pageList = illcaseList.subList(fromIndex, toIndex);
        long total=illcaseList.size();
        return getStringObjectMapOut(pageList,total);
    }

    @NotNull
    static Map<String, Object> getStringObjectMap(IPage<Illcase> casePage, QueryWrapper<Illcase> caseQueryWrapper, CaseMapper caseMapper) {
        long total=caseMapper.selectCount(caseQueryWrapper);
        casePage = caseMapper.selectPage(casePage, caseQueryWrapper);
        Map<String, Object> caseMap = new HashMap<>();
        List<Illcase> illcaseList =casePage.getRecords();
        return getStringObjectMapOut(illcaseList,total);
    }

    @NotNull
    static Map<String, Object> getStringObjectMapOut(List<Illcase> illcaseList,long total) {
        Map<String, Object> caseMap = new HashMap<>();
        if(illcaseList !=null && !illcaseList.isEmpty()) {
            caseMap.put("error_message", "success");
            caseMap.put("case_list", illcaseList);
            caseMap.put("total",total);
        } else{
            caseMap.put("error_message", "未找到对应case");
        }

        return new JSONObject(caseMap);
    }

    @NotNull
    private Map<String, Object> getStringObjectMap(Integer page, Integer pageSize, List<Ill> illList) {
        List<Integer> illIdList = new ArrayList<>();
        for (Ill ill : illList) {
            illIdList.add(ill.getIllId());
        }
        IPage<Illcase> casePage = new Page<>(page, pageSize);
        casePage = caseMapper.selectPage(casePage, Wrappers.<Illcase>lambdaQuery()
                .in(Illcase::getIllId, illIdList));
        List<Illcase> illcaseList =casePage.getRecords();

        long total=illIdList.size();
        return getStringObjectMapOut(illcaseList,total);
    }



}
