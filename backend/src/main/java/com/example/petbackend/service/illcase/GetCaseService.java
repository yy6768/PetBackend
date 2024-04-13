package com.example.petbackend.service.illcase;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

public interface GetCaseService {
    Map<String, Object> getByCateCase(Integer page, Integer pageSize,String cate_name);

    Map<String, Object> getByIllCase(Integer page, Integer pageSize,String ill_name);

    Map<String, Object> getByDateCase(Integer page, Integer pageSize, LocalDateTime start,LocalDateTime end);
}
