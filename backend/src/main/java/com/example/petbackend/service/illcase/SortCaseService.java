package com.example.petbackend.service.illcase;

import java.util.Map;

public interface SortCaseService {
    Map<String, Object> sortByIdCase();

    Map<String, Object> sortByDoctorCase();

    Map<String, Object> sortByDateCase();
}
