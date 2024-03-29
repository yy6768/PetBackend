package com.example.petbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.petbackend.dto.CaseLabDTO;
import com.example.petbackend.dto.CaseMedicineDTO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CaseMedicineMapper extends BaseMapper<CaseMedicineDTO> {
    @Delete("delete from case_medicine where cid=#{cid} and medicine_id=#{medicineId}")
    int deleteById(Integer cid, Integer medicineId);
}
