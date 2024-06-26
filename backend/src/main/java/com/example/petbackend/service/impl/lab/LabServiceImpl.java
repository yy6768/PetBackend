package com.example.petbackend.service.impl.lab;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petbackend.mapper.CaseLabMapper;
import com.example.petbackend.mapper.LabMapper;
import com.example.petbackend.pojo.Lab;
import com.example.petbackend.service.lab.LabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LabServiceImpl implements LabService {

    @Autowired
    private LabMapper labMapper;
    @Autowired
    private CaseLabMapper caseLabMapper;

    @Override
    public Map<String, String> addLab(String lab_name, Double lab_cost, String description) {
        Lab lab = new Lab(null, lab_name, lab_cost, description);
        labMapper.insert(lab);
        Map<String,String> labMap=new HashMap<>();
        labMap.put("error_message", "success");
        labMap.put("lab_id", String.valueOf(lab.getLabId()));
        return labMap;
    }

    @Override
    public Map<String, String> updateLab(Integer lab_id,String lab_name, Double lab_cost, String description) {
        Lab lab = labMapper.selectById(lab_id);
        int res = 0;
        if(lab != null){
            lab.setLabName(lab_name);
            lab.setLabCost(lab_cost);
            lab.setDescription(description);
            res=labMapper.updateById(lab);
        }
        Map<String,String> labMap=new HashMap<>();
        if(res < 1){
            labMap.put("error_message", "update fail");
        }
        else{
            labMap.put("error_message", "success");
        }
        return labMap;
    }

    @Override
    public Map<String, String> deleteLab(Integer lab_id) {
        int clres= caseLabMapper.deleteByLabId(lab_id);
        int result=labMapper.deleteById(lab_id);

        Map<String,String> labMap=new HashMap<>();
        if(result < 1||clres<1){
            labMap.put("error_message", "delete fail");
        }
        else{
            labMap.put("error_message", "success");
        }
        return labMap;
    }

    @Override
    public Map<String, Object> getAllLab(Integer page, Integer pageSize,String search) {
        IPage<Lab> labPage = new Page<>(page, pageSize);
        QueryWrapper<Lab> labQueryWrapper = new QueryWrapper<>();
        labQueryWrapper.like("lab_name", search);
        labPage = labMapper.selectPage(labPage, labQueryWrapper);
        Map<String, Object> labMap = new HashMap<>();
        List<Lab> labList=labPage.getRecords();
        if(labList !=null && !labList.isEmpty()) {
            labMap.put("error_message", "success");
            labMap.put("lab_list", labList);
            labMap.put("total",labMapper.selectCount(labQueryWrapper));
        } else{
            labMap.put("error_message", "未找到对应实验项目");
        }

        return new JSONObject(labMap);
    }
}
