package com.example.petbackend.service.illcase;

import java.util.Map;

public interface SortCaseService {
    Map<String, Object> sortByIdCase(Integer page, Integer pageSize);

    Map<String, Object> sortByDoctorCase(Integer page, Integer pageSize);

    Map<String, Object> sortByDateCase(Integer page, Integer pageSize);
}
