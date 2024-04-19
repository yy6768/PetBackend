package com.example.petbackend.service.impl.illcase;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.example.petbackend.dto.LabDTO;
import com.example.petbackend.dto.MedicineDTO;
import com.example.petbackend.mapper.*;
import com.example.petbackend.pojo.*;
import com.example.petbackend.service.illcase.CaseService;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchRequest;

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
    @Autowired
    private RestHighLevelClient client;

    @Override
    public Map<String, String> addCase(String username, String ill_name, LocalDateTime date,
                                       String basic_situation,String photo,
                                       String result, String therapy,String surgery_video) {
        Map<String,String> caseMap=new HashMap<>();
        User user=userMapper.selectByName(username).get(0);
        Ill ill= illMapper.selectByName(ill_name).get(0);
        if(user==null||ill==null){
            caseMap.put("error_message", "未找到对应用户和病种");
        }
        else{
            Illcase illcase = new Illcase(user.getUid(), ill.getIllId(), date,basic_situation,photo,result,therapy,surgery_video);
            caseMapper.insert(illcase);

            try{
                IllcaseDoc illcaseDoc = new IllcaseDoc();
                illcaseDoc.setCid(illcase.getCid());
                illcaseDoc.setUsername(username);
                illcaseDoc.setCateName(cateMapper.selectById(illMapper.selectById(illcase.getIllId()).getCateId()).getCateName());
                illcaseDoc.setIllName(ill_name);
                illcaseDoc.setResult(result);
                illcaseDoc.setTherapy(therapy);
                illcaseDoc.setBasicSituation(basic_situation);

                IndexRequest request = new IndexRequest("illcase").id(illcase.getCid().toString());
                request.source(com.alibaba.fastjson.JSON.toJSONString(illcaseDoc), XContentType.JSON);
                client.index(request, RequestOptions.DEFAULT);
            }catch (IOException e){
                throw new RuntimeException(e);
            }

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
            else if(basic_situation.length()>10000){
                caseMap.put("error_message", "病例描述过");
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
            caseMap.put("error_message", "更新失败");
        }
        else{
            caseMap.put("error_message", "success");
            try{
                UpdateRequest request = new UpdateRequest("illcase", cid.toString());
                request.doc(
                        "basicSituation",basic_situation,
                        "result", result,
                        "therapy", therapy
                );
                client.update(request, RequestOptions.DEFAULT);
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }
        return caseMap;
    }

    @Override
    public Map<String, String> deleteCase(Integer cid) {
        int cmres= caseMedicineMapper.deleteByCaseId(cid);
        int clres= caseLabMapper.deleteByCaseId(cid);
        int result=caseMapper.deleteById(cid);
        Map<String,String> caseMap=new HashMap<>();
        if(cmres < 1 && result<1 && clres<1){
            caseMap.put("error_message", "删除失败");
        }
        else if(cmres < 1){
            caseMap.put("error_message", "对应化验项目删除失败");
        }
        else if(clres < 1){
            caseMap.put("error_message", "对应化验项目删除失败");
        }
        else{
            caseMap.put("error_message", "success");
            try{
                DeleteRequest request = new DeleteRequest("illcase", cid.toString());
                client.delete(request, RequestOptions.DEFAULT);
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }
        return caseMap;
    }

//    @Override
//    public Map<String, Object> getAllCase(Integer page, Integer pageSize,String search) {
//        List<Illcase> illcases = caseMapper.selectBySearch(search);
//        List<Integer> illCaseIdList = new ArrayList<>();
//        for (Illcase illcase : illcases) {
//            illCaseIdList.add(illcase.getCid());
//        }
//        IPage<Illcase> casePage = new Page<>(page, pageSize);
//        QueryWrapper<Illcase> caseQueryWrapper = new QueryWrapper<>();
//        Map<String, Object> caseMap = new HashMap<>();
//        casePage = caseMapper.selectPage(casePage, Wrappers.<Illcase>lambdaQuery()
//                .in(Illcase::getCid, illCaseIdList));
//        List<Illcase> illcaseList=casePage.getRecords();
//        List<IllcaseDTO> illcaseDTOList =new ArrayList<>();
//        for(Illcase illcase: illcaseList){
//            Ill ill = illMapper.selectById(illcase.getIllId());
//            Cate cate = cateMapper.selectById(ill.getCateId());
//            User user=userMapper.selectById(illcase.getUid());
//            IllcaseDTO illcaseDTO = new IllcaseDTO();
//            illcaseDTO.setCid(illcase.getCid());
//            illcaseDTO.setDate(illcase.getDate());
//            illcaseDTO.setCate_name(cate.getCateName());
//            illcaseDTO.setUsername(user.getUsername());
//            illcaseDTO.setIll_name(ill.getIllName());
//            illcaseDTOList.add(illcaseDTO);
//        }
//        if(!illcaseDTOList.isEmpty()) {
//            caseMap.put("error_message", "success");
//            caseMap.put("case_list", illcaseDTOList);
//            caseMap.put("total", caseMapper.selectCount(caseQueryWrapper));
//        } else{
//            caseMap.put("error_message", "未找到对应病例");
//        }
//
//        return new JSONObject(caseMap);
//
//    }

    @Override
    public Map<String, Object> getAllCase(Integer page, Integer pageSize,String search) {
        try{
            SearchRequest request = new SearchRequest("illcase");
            if (search == null || "".equals(search)) {
                request.source().query(QueryBuilders.matchAllQuery());
            } else {
                request.source().query(QueryBuilders.matchQuery("all", search));
            }

            request.source().from((page - 1) * pageSize).size(pageSize);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHits searchHits = response.getHits();
            long total = searchHits.getTotalHits().value;
            System.out.println("共搜索到" + total + "条数据");
            SearchHit[] hits = searchHits.getHits();
            List<IllcaseDoc> illcaseDocList=new ArrayList<>();
            for (SearchHit hit : hits) {
                String json = hit.getSourceAsString();
                IllcaseDoc illcaseDoc = JSON.parseObject(json, IllcaseDoc.class);
                illcaseDocList.add(illcaseDoc);
            }
            Map<String, Object> caseMap = new HashMap<>();
            if(!illcaseDocList.isEmpty()) {
            caseMap.put("error_message", "success");
            caseMap.put("case_list", illcaseDocList);
            caseMap.put("total", total);
        } else{
            caseMap.put("error_message", "未找到对应病例");
        }
            return caseMap;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
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
            caseMap.put("error_message", "未找到id对应的病例");
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
            labMap.put("error_message", "获取病例列表失败");
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
            medicineMap.put("error_message", "获取药品失败");
        }
        else {
            medicineMap.put("error_message", "success");
        }
        medicineMap.put("medicine_list", medicineList);

        return new JSONObject(medicineMap);
    }

}
