package com.example.petbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.petbackend.pojo.CaseLab;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CaseLabMapper extends BaseMapper<CaseLab> {

    @Delete("delete from case_lab where cid=#{cid} and lab_id=#{labId}")
    int deleteById(Integer cid, Integer labId);

    @Delete("delete from case_lab where cid=#{cid}")
    int deleteByCaseId(Integer cid);

    @Delete("delete from case_lab where lab_id=#{labId}")
    int deleteByLabId(Integer labId);
}
