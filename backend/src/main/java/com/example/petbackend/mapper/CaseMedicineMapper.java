package com.example.petbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.petbackend.pojo.CaseMedicine;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CaseMedicineMapper extends BaseMapper<CaseMedicine> {
    @Delete("delete from case_medicine where cid=#{cid} and medicine_id=#{medicineId}")
    int deleteById(Integer cid, Integer medicineId);

    @Delete("delete from case_medicine where medicine_id=#{medicineId}")
    int deleteByMedId(Integer medicineId);

    @Delete("delete from case_medicine where cid=#{cid}")
    int deleteByCaseId(Integer cid);

    @Select("select * from case_medicine where cid=#{cid}")
    List<CaseMedicine> selectByCid(Integer cid);
}
