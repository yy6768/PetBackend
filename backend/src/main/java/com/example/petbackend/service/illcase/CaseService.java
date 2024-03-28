package com.example.petbackend.service.illcase;

import java.util.Date;
import java.util.Map;

public interface CaseService {
    Map<String,String> addCase(Integer uid, Integer ill_id, Date date,
                               String basic_situation, String photo, String result,
                               String therapy, String surgery_video);

    Map<String,String> updateCase(Integer cid,Integer ill_id,
                                  String basic_situation,String result,
                                  String therapy);

    Map<String,String> deleteCase(Integer cid);

    Map<String,Object> getAllCase();
}
