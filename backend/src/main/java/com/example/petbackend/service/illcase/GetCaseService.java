package com.example.petbackend.service.illcase;

import java.util.Date;
import java.util.Map;

public interface GetCaseService {
    Map<String, Object> getByCateCase(String cate_name);

    Map<String, Object> getByIllCase(String ill_name);

    Map<String, Object> getByDateCase(Date date);
}
