package com.example.petbackend.service.illcase;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

public interface CaseService {
    Map<String,String> addCase(String username, String ill_name, LocalDateTime date, String basic_situation, String photo,
                               String result, String therapy, String surgery_video);

    Map<String,String> updateCase(Integer cid, String basic_situation,String photo,
                                  String result, String therapy,String surgery_video);

    Map<String,String> deleteCase(Integer cid);

    Map<String,Object> getAllCase(Integer page, Integer pageSize,String search);

    Map<String, Object> getByIdCase(Integer cid);

    Map<String, Object> getAllLab();

    Map<String, Object> getAllMedicine();

    boolean createIllcaseIndex(String index) throws IOException;
}
