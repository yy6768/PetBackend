package com.example.petbackend.service.impl.illcase;

import com.alibaba.fastjson.JSONObject;
import com.example.petbackend.mapper.CaseMapper;
import com.example.petbackend.pojo.Illcase;
import com.example.petbackend.service.illcase.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CaseServiceImpl implements CaseService {

    @Autowired
    private CaseMapper caseMapper;

    @Override
    public Map<String, String> addCase(Integer uid, Integer ill_id, Date date,
                                       String basic_situation, String photo,
                                       String result, String therapy,
                                       String surgery_video) {
        Illcase illcase = new Illcase(uid,ill_id,date,basic_situation,photo,result,
                therapy,surgery_video);
        Map<String,String> caseMap=new HashMap<>();
        caseMapper.insert(illcase);
        caseMap.put("error_message", "success");
        caseMap.put("cid", String.valueOf(illcase.getCid()));
        return caseMap;
    }

    @Override
    public Map<String, String> updateCase(Integer cid, Integer ill_id,
                                          String basic_situation, String result,
                                          String therapy) {
        Illcase illcase = caseMapper.selectById(cid);
        int res=0;
        if(illcase!=null){
            illcase.setCid(cid);
            illcase.setIllId(ill_id);
            illcase.setBasicSituation(basic_situation);
            illcase.setResult(result);
            illcase.setTherapy(therapy);
            res=caseMapper.updateById(illcase);
        }
        Map<String,String> caseMap=new HashMap<>();
        if(res < 1){
            caseMap.put("error_message", "update fail");
        }
        else{
            caseMap.put("error_message", "success");
        }
        return caseMap;
    }

    @Override
    public Map<String, String> deleteCase(Integer cid) {
        Map<String,String> caseMap=new HashMap<>();
        if(caseMapper.deleteById(cid) < 1){
            caseMap.put("error_message", "delete fail");
        }
        else{
            caseMap.put("error_message", "success");
        }
        return caseMap;
    }

    @Override
    public Map<String, Object> getAllCase() {
        Map<String, Object> caseMap = new HashMap<>();
        List<Illcase> illcaseList =caseMapper.getAll();
        if(illcaseList ==null){
            caseMap.put("error_message", "get all fail");
        }
        else {
            caseMap.put("error_message", "success");
        }
        caseMap.put("case_list", illcaseList);

        JSONObject obj = new JSONObject(caseMap);
        return obj;

    }

    @Override
    public Map<String, String> getByIdCase(Integer cid) {
        Illcase illcase = caseMapper.selectById(cid);
        Map<String,String> caseMap=new HashMap<>();
        if(illcase!=null){
            caseMap.put("error_message", "success");
        }
        else{
            caseMap.put("error_message", "update fail");
        }
        caseMap.put("illcase", String.valueOf(illcase));

        return caseMap;
    }
}
