package com.example.petbackend.service.impl.ill;

import com.alibaba.fastjson.JSONObject;
import com.example.petbackend.mapper.IllMapper;
import com.example.petbackend.pojo.Ill;
import com.example.petbackend.pojo.Illcase;
import com.example.petbackend.service.ill.IllService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IllServiceImpl implements IllService {
    @Autowired
    private IllMapper illMapper;
    @Override
    public Map<String, Object> getAllInCateIll(String cate_id) {
        List<Ill> illList =illMapper.getAllInCate(cate_id);
        return getStringObjectMap(illList);
    }

    @NotNull
    private Map<String, Object> getStringObjectMap(List<Ill> illList) {
        Map<String, Object> illMap = new HashMap<>();
        if(illList ==null){
            illMap.put("error_message", "get list fail");
        }
        else {
            illMap.put("error_message", "success");
        }
        illMap.put("ill_list", illList);

        JSONObject obj = new JSONObject(illMap);
        return obj;
    }
}
