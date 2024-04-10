package com.example.petbackend.service.impl.illcase;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petbackend.dto.IllcaseDTO;
import com.example.petbackend.dto.QuestionDTO;
import com.example.petbackend.mapper.*;
import com.example.petbackend.pojo.*;
import com.example.petbackend.service.illcase.CaseService;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static com.example.petbackend.service.impl.illcase.GetCaseServiceImpl.getStringObjectMap;

@Service
public class CaseServiceImpl implements CaseService {

    @Autowired
    private CaseMapper caseMapper;
    @Autowired
    private LabMapper labMapper;
    @Autowired
    private MedicineMapper medicineMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IllMapper illMapper;
    @Autowired
    private CateMapper cateMapper;

    @Override
    public Map<String, String> addCase(Integer uid, Integer ill_id, Date date,
                                       String basic_situation, String photo,
                                       String result, String therapy,
                                       String surgery_video) {
        Map<String,String> caseMap=new HashMap<>();
        User user=userMapper.selectById(uid);
        Ill ill= illMapper.selectById(ill_id);
        if(user==null||ill==null){
            caseMap.put("error_message", "未找到对应user或ill");
        }
        else if(basic_situation.length()>255){
            caseMap.put("error_message", "basic_situation是长文本");
        }
        else{
            if(photo==null||photo.length()==0)photo="http://tecentapi.empty.image";
            Illcase illcase = new Illcase(uid,ill_id,date,basic_situation,photo,result,
                    therapy,surgery_video);
            caseMapper.insert(illcase);
            caseMap.put("error_message", "success");
            caseMap.put("cid", String.valueOf(illcase.getCid()));
        }
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
    public Map<String, Object> getAllCase(Integer page, Integer pageSize,String search) {
        IPage<Illcase> casePage = new Page<>(page, pageSize);
        QueryWrapper<Illcase> caseQueryWrapper = new QueryWrapper<>();
        caseQueryWrapper.like("basic_situation", search);
        casePage = caseMapper.selectPage(casePage, caseQueryWrapper);
        Map<String, Object> caseMap = new HashMap<>();
        List<Illcase> illcaseList=casePage.getRecords();
        List<IllcaseDTO> illcaseDTOList =new ArrayList<>();
        for(Illcase illcase: illcaseList){
            Ill ill = illMapper.selectById(illcase.getIllId());
            Cate cate = cateMapper.selectById(ill.getCateId());
            User user=userMapper.selectById(illcase.getUid());
            IllcaseDTO illcaseDTO = new IllcaseDTO();
            illcaseDTO.setCid(illcase.getCid());
            illcaseDTO.setDate(illcase.getDate());
            illcaseDTO.setCate_name(cate.getCateName());
            illcaseDTO.setUsername(user.getUsername());
            illcaseDTO.setIll_name(ill.getIllName());
            illcaseDTOList.add(illcaseDTO);
        }
        if(!illcaseDTOList.isEmpty()) {
            caseMap.put("error_message", "success");
            caseMap.put("case_list", illcaseDTOList);
            caseMap.put("total", caseMapper.selectCount(caseQueryWrapper));
        } else{
            caseMap.put("error_message", "未找到对应case");
        }

        return new JSONObject(caseMap);

    }

    @Override
    public Map<String, String> getByIdCase(Integer cid) {
        Illcase illcase = caseMapper.selectById(cid);
        Map<String,String> caseMap=new HashMap<>();
        if(illcase!=null){
            caseMap.put("error_message", "success");
        }
        else{
            caseMap.put("error_message", "get case by id fail");
        }
        caseMap.put("illcase", String.valueOf(illcase));

        return caseMap;
    }

    @Override
    public Map<String, Object> getAllLab() {
        Map<String, Object> labMap = new HashMap<>();
        List<Lab> labList =labMapper.getAll();
        if(labList ==null){
            labMap.put("error_message", "get all fail");
        }
        else {
            labMap.put("error_message", "success");
        }
        labMap.put("lab_list", labList);

        JSONObject obj = new JSONObject(labMap);
        return obj;
    }

    @Override
    public Map<String, Object> getAllMedicine() {
        Map<String, Object> medicineMap = new HashMap<>();
        List<Medicine> medicineList =medicineMapper.getAll();
        if(medicineList ==null){
            medicineMap.put("error_message", "get all fail");
        }
        else {
            medicineMap.put("error_message", "success");
        }
        medicineMap.put("medicine_list", medicineList);

        JSONObject obj = new JSONObject(medicineMap);
        return obj;
    }

    @Override
    public boolean createIllcaseIndex(String index) throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
        createIndexRequest.settings(Settings.builder()
                .put("index.number_of_shards", 1)
                .put("index.number_of_replicas", 0)
        );
        createIndexRequest.mapping("{\n" +
                "  \"properties\": {\n" +
                "    \"cid\": {\n" +
                "      \"type\": \"integer\"\n" +
                "    },\n" +
                "    \"username\": {\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "    \"ill_name\": {\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "    \"date\": {\n" +
                "      \"type\": \"date\"\n" +
                "    },\n" +
                "    \"basic_situation\": {\n" +
                "      \"type\": \"string\"\n" +
                "    }\n" +
                "    \"photo\": {\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "    \"result\": {\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "    \"therapy\": {\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "    \"surgery_video\": {\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "  }\n" +
                "}", XContentType.JSON);
        RestHighLevelClient client = null;
        CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        return createIndexResponse.isAcknowledged();
    }
}
