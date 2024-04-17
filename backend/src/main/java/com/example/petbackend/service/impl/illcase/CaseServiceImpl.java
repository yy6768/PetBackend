package com.example.petbackend.service.impl.illcase;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petbackend.dto.IllcaseDTO;
import com.example.petbackend.dto.LabDTO;
import com.example.petbackend.dto.MedicineDTO;
import com.example.petbackend.mapper.*;
import com.example.petbackend.pojo.*;
import com.example.petbackend.service.illcase.CaseService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


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
    @Autowired
    private CaseMedicineMapper caseMedicineMapper;
    @Autowired
    private CaseLabMapper caseLabMapper;

    @Override
    public Map<String, String> addCase(String username, String ill_name, LocalDateTime date,
                                       String basic_situation,String photo,
                                       String result, String therapy,String surgery_video) {
        Map<String,String> caseMap=new HashMap<>();
        User user=userMapper.selectByName(username).get(0);
        Ill ill= illMapper.selectByName(ill_name).get(0);
        if(user==null||ill==null){
            caseMap.put("error_message", "未找到对应user或ill");
        }
        else{
            Illcase illcase = new Illcase(user.getUid(), ill.getIllId(), date,basic_situation,photo,result,therapy,surgery_video);
            caseMapper.insert(illcase);
            caseMap.put("error_message", "success");
            caseMap.put("cid", String.valueOf(illcase.getCid()));
        }
        return caseMap;
    }

    @Override
    public Map<String, String> updateCase(Integer cid, String basic_situation, String photo,
                                          String result, String therapy,String surgery_video) {
        Illcase illcase = caseMapper.selectById(cid);
        int res=0;
        Map<String,String> caseMap=new HashMap<>();
        if(illcase!=null){
            if(basic_situation==null){
                basic_situation="暂无内容";
            }
            else if(basic_situation.length()>255){
                caseMap.put("error_message", "basic_situation是长文本");
                return caseMap;
            }
            if(photo==null||photo.length()==0)photo="http://tecentapi.empty.image";
            illcase.setBasicSituation(basic_situation);
            illcase.setPhoto(photo);
            illcase.setResult(result);
            illcase.setTherapy(therapy);
            illcase.setSurgeryVideo(surgery_video);
            res=caseMapper.updateById(illcase);
        }
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
        int cmres= caseMedicineMapper.deleteByCaseId(cid);
        int clres= caseLabMapper.deleteByCaseId(cid);
        int result=caseMapper.deleteById(cid);
        Map<String,String> caseMap=new HashMap<>();
        if(cmres < 1||result<1||clres<1){
            caseMap.put("error_message", "delete fail");
        }
        else{
            caseMap.put("error_message", "success");
        }
        return caseMap;
    }

    @Override
    public Map<String, Object> getAllCase(Integer page, Integer pageSize,String search) {
        List<Illcase> illcases = caseMapper.selectBySearch(search);
        List<Integer> illCaseIdList = new ArrayList<>();
        for (Illcase illcase : illcases) {
            illCaseIdList.add(illcase.getCid());
        }
        IPage<Illcase> casePage = new Page<>(page, pageSize);
        QueryWrapper<Illcase> caseQueryWrapper = new QueryWrapper<>();
        Map<String, Object> caseMap = new HashMap<>();
        casePage = caseMapper.selectPage(casePage, Wrappers.<Illcase>lambdaQuery()
                .in(Illcase::getCid, illCaseIdList));
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
    public Map<String, Object> getByIdCase(Integer cid) {
        Illcase illcase = caseMapper.selectById(cid);
        List<CaseLab> caseLabList=caseLabMapper.selectByCid(cid);
        List<LabDTO> labDTOList=new ArrayList<>();
        for(CaseLab caseLab:caseLabList){
            Lab lab=labMapper.selectById(caseLab.getLabId());
            LabDTO labDTO=new LabDTO(lab.getLabId(),lab.getLabName(),lab.getLabCost(),caseLab.getLabResult(),caseLab.getLabPhoto());
            labDTOList.add(labDTO);
        }
        List<CaseMedicine> caseMedicineList=caseMedicineMapper.selectByCid(cid);
        List<MedicineDTO> medicineDTOList=new ArrayList<>();
        for(CaseMedicine caseMedicine:caseMedicineList){
            Medicine medicine=medicineMapper.selectById(caseMedicine.getMedicineId());
            MedicineDTO medicineDTO=new MedicineDTO(medicine.getMedicineId(),medicine.getMedicineName(),medicine.getMedicineCost());
            medicineDTOList.add(medicineDTO);
        }

        Map<String,Object> caseMap=new HashMap<>();
        if(illcase!=null){
            caseMap.put("error_message", "success");
        }
        else{
            caseMap.put("error_message", "get case by id fail");
        }
        caseMap.put("case", illcase);
        caseMap.put("lab_item",labDTOList);
        caseMap.put("medicine_item",medicineDTOList);
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

        return new JSONObject(labMap);
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

        return new JSONObject(medicineMap);
    }

    @Override
    public boolean createIllcaseIndex(String index) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));
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
                "    },\n" +
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

        try {
            CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            return createIndexResponse.isAcknowledged();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            // 不要忘记关闭client连接
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
//        return createIndexResponse.isAcknowledged();

    }
}
