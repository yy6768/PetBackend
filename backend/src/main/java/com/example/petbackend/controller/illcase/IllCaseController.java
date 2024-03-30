package com.example.petbackend.controller.illcase;

import com.example.petbackend.service.illcase.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public Map<String,String> addCase(@RequestParam Map<String,String> map) throws ParseException {
        Integer uid= Integer.valueOf(map.get("uid"));
        Integer ill_id= Integer.valueOf(map.get("ill_id"));
        //String转Date
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(map.get("date"));

        String basic_situation=map.get("basic_situation");
        String photo=map.get("photo");
        String result=map.get("result");
        String therapy=map.get("therapy");
        String surgery_video=map.get("surgery_video");

        return caseService.addCase(uid,ill_id,date,basic_situation,photo,result,
                therapy,surgery_video);
    }

    @PostMapping("/case/update")
    public Map<String,String> updateCase(@RequestParam Map<String,String> map){
        Integer cid= Integer.valueOf(map.get("cid"));
        Integer ill_id= Integer.valueOf(map.get("ill_id"));
        String basic_situation=map.get("basic_situation");
        String result=map.get("result");
        String therapy=map.get("therapy");

        return caseService.updateCase(cid,ill_id,basic_situation,result,therapy);
    }

    @DeleteMapping("/case/delete")
    public Map<String,String> deleteCase(@RequestParam Map<String,String> map){
        Integer cid=Integer.valueOf(map.get("cid"));

        return caseService.deleteCase(cid);
    }

    @GetMapping("/case/getall")
    public Map<String, Object> getAllCase(){

        return caseService.getAllCase();
    }

    @GetMapping("/case/get_by_id")
    public Map<String,String> getByIdCase(@RequestParam Map<String,String> map){
        Integer cid=Integer.valueOf(map.get("cid"));

        return caseService.getByIdCase(cid);
    }

    @GetMapping("/case/get_by_cate")
    public Map<String,Object> getByCateCase(@RequestParam Map<String,String> map){
        String cate_name=map.get("cate_name");

        return getCaseService.getByCateCase(cate_name);
    }

    @GetMapping("/case/get_by_ill")
    public Map<String,Object> getByIllCase(@RequestParam Map<String,String> map){
        String ill_name=map.get("ill_name");

        return getCaseService.getByIllCase(ill_name);
    }

    @GetMapping("/case/get_by_date")
    public Map<String,Object> getByDateCase(@RequestParam Map<String,String> map) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(map.get("date"));

        return getCaseService.getByDateCase(date);
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
    public Map<String, Object> sortByIdCase(){

        return sortCaseService.sortByIdCase();
    }
    @GetMapping("/case/sort_by_doctor")
    public Map<String, Object> sortByDoctorCase(){

        return sortCaseService.sortByDoctorCase();
    }

    @GetMapping("/case/sort_by_date")
    public Map<String, Object> sortByDateCase(){

        return sortCaseService.sortByDateCase();
    }


}
