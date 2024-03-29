package com.example.petbackend.service.impl.cate;

import com.alibaba.fastjson.JSONObject;
import com.example.petbackend.mapper.CateMapper;
import com.example.petbackend.pojo.Cate;
import com.example.petbackend.pojo.Illcase;
import com.example.petbackend.service.cate.CateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CateServiceImpl implements CateService {
    @Autowired
    private CateMapper cateMapper;
    @Override
    public Map<String, Object> getAllCate() {
        Map<String, Object> cateMap = new HashMap<>();
        List<String> cateList =cateMapper.getAll();
        if(cateList ==null){
            cateMap.put("error_message", "get all fail");
        }
        else {
            cateMap.put("error_message", "success");
        }
        cateMap.put("cate_list", cateList);

        JSONObject obj = new JSONObject(cateMap);
        return obj;
    }
}
