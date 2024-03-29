package com.example.petbackend.service.illcase;

import java.util.Map;

public interface UpdateCaseService {
    Map<String, String> addLabCase(Integer cid, Integer lab_id, String lab_result,
                                   String lab_photo);

    Map<String, String> deleteLabCase(Integer cid, Integer lab_id);

    Map<String, String> addMedicineCase(Integer cid, Integer medicine_id);

    Map<String, String> deleteMedicineCase(Integer cid, Integer medicineId);
}
