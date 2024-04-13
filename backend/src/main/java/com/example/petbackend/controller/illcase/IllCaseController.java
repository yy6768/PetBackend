package com.example.petbackend.controller.illcase;

import com.example.petbackend.service.illcase.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class IllCaseController {
    @Autowired
    private CaseService caseService;
    @Autowired
    private GetCaseService getCaseService;
    @Autowired
    private UpdateCaseService updateCaseService;
    @Autowired
    private SortCaseService sortCaseService;

    @PostMapping("/case/add")
    public Map<String,String> addCase(@RequestParam Map<String,String> map) {
        String username= map.get("username");
        String ill_name= map.get("ill_name");
        LocalDateTime date= LocalDateTime.now();
        String basic_situation=map.get("basic_situation");
        String photo=map.get("photo");
        String result=map.get("result");
        String therapy=map.get("therapy");
        String surgery_video=map.get("surgery_video");

        return caseService.addCase(username,ill_name,date,basic_situation,photo,result,therapy,surgery_video);
    }

    @PostMapping("/case/update")
    public Map<String,String> updateCase(@RequestParam Map<String,String> map){
        Integer cid= Integer.valueOf(map.get("cid"));
        String basic_situation=map.get("basic_situation");
        String photo=map.get("photo");
        String result=map.get("result");
        String therapy=map.get("therapy");
        String surgery_video=map.get("surgery_video");

        return caseService.updateCase(cid,basic_situation,photo,result,therapy,surgery_video);
    }

    @DeleteMapping("/case/delete")
    public Map<String,String> deleteCase(@RequestParam Map<String,String> map){
        Integer cid=Integer.valueOf(map.get("cid"));

        return caseService.deleteCase(cid);
    }

    @GetMapping("/case/getall")
    public Map<String, Object> getAllCase(@RequestParam Map<String, String> map){
        Integer page = Integer.valueOf(map.get("page"));
        Integer pageSize = Integer.valueOf(map.get("pageSize"));
        String search = map.get("search");
        return caseService.getAllCase(page, pageSize,search);
    }

    @GetMapping("/case/get_by_id")
    public Map<String,Object> getByIdCase(@RequestParam Map<String,String> map){
        Integer cid=Integer.valueOf(map.get("cid"));

        return caseService.getByIdCase(cid);
    }

    @GetMapping("/case/get_by_cate")
    public Map<String,Object> getByCateCase(@RequestParam Map<String,String> map){
        Integer page = Integer.valueOf(map.get("page"));
        Integer pageSize = Integer.valueOf(map.get("pageSize"));
        String cate_name= map.get("cate_name");

        return getCaseService.getByCateCase(page, pageSize,cate_name);
    }

    @GetMapping("/case/get_by_ill")
    public Map<String,Object> getByIllCase(@RequestParam Map<String,String> map){
        Integer page = Integer.valueOf(map.get("page"));
        Integer pageSize = Integer.valueOf(map.get("pageSize"));
        String ill_name=map.get("ill_name");

        return getCaseService.getByIllCase(page, pageSize,ill_name);
    }

    @GetMapping("/case/get_by_date")
    public Map<String,Object> getByDateCase(@RequestParam Map<String,String> map) throws ParseException {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(map.get("start"),df);
        LocalDateTime end = LocalDateTime.parse(map.get("end"),df);
        Integer page = Integer.valueOf(map.get("page"));
        Integer pageSize = Integer.valueOf(map.get("pageSize"));
        return getCaseService.getByDateCase(page, pageSize,start,end);
    }

    @PostMapping("/case/add_lab")
    public Map<String,String> addLabCase(@RequestParam Map<String,String> map){
        Integer cid= Integer.valueOf(map.get("cid"));
        Integer lab_id= Integer.valueOf(map.get("lab_id"));
        String lab_result=map.get("lab_result");
        String lab_photo=map.get("lab_photo");

        return updateCaseService.addLabCase(cid,lab_id,lab_result,lab_photo);
    }

    @DeleteMapping("/case/delete_lab")
    public Map<String,String> deleteLabCase(@RequestParam Map<String,String> map){
        Integer cid=Integer.valueOf(map.get("cid"));
        Integer lab_id= Integer.valueOf(map.get("lab_id"));

        return updateCaseService.deleteLabCase(cid,lab_id);
    }

    @PostMapping("/case/add_medicine")
    public Map<String,String> addMedicineCase(@RequestParam Map<String,String> map){
        Integer cid= Integer.valueOf(map.get("cid"));
        Integer medicine_id= Integer.valueOf(map.get("medicine_id"));

        return updateCaseService.addMedicineCase(cid,medicine_id);
    }

    @DeleteMapping("/case/delete_medicine")
    public Map<String,String> deleteMedicineCase(@RequestParam Map<String,String> map){
        Integer cid=Integer.valueOf(map.get("cid"));
        Integer medicine_id= Integer.valueOf(map.get("medicine_id"));

        return updateCaseService.deleteMedicineCase(cid,medicine_id);
    }

    @GetMapping("/case/sort_by_id")
    public Map<String, Object> sortByIdCase(@RequestParam Map<String,String> map){
        Integer page = Integer.valueOf(map.get("page"));
        Integer pageSize = Integer.valueOf(map.get("pageSize"));
        return sortCaseService.sortByIdCase(page,pageSize);
    }
    @GetMapping("/case/sort_by_doctor")
    public Map<String, Object> sortByDoctorCase(@RequestParam Map<String,String> map){
        Integer page = Integer.valueOf(map.get("page"));
        Integer pageSize = Integer.valueOf(map.get("pageSize"));
        return sortCaseService.sortByDoctorCase(page,pageSize);
    }

    @GetMapping("/case/sort_by_date")
    public Map<String, Object> sortByDateCase(@RequestParam Map<String,String> map){
        Integer page = Integer.valueOf(map.get("page"));
        Integer pageSize = Integer.valueOf(map.get("pageSize"));
        return sortCaseService.sortByDateCase(page,pageSize);
    }

    @GetMapping("/case/get_all_lab")
    public Map<String, Object> getAllLab(){

        return caseService.getAllLab();
    }

    @GetMapping("/case/get_all_medicine")
    public Map<String, Object> getAllMedicine(){

        return caseService.getAllMedicine();
    }

    @PutMapping("/case/new_index")
    public boolean createIllcaseIndex(String index) throws IOException {
        return caseService.createIllcaseIndex(index);
    }

}
