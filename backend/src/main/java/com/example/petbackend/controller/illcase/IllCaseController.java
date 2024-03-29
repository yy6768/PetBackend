package com.example.petbackend.controller.illcase;

import com.example.petbackend.service.illcase.CaseService;
import com.example.petbackend.service.illcase.GetCaseService;
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
    private GetCaseService getCaseService;

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
        Integer cate_id=Integer.valueOf(map.get("cate_id"));

        return getCaseService.getByCateCase(cate_id);
    }
}
