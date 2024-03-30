package com.example.petbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.petbackend.dto.CaseLabDTO;
import com.example.petbackend.pojo.Illcase;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CaseLabMapper extends BaseMapper<CaseLabDTO> {

    @Delete("delete from case_lab where cid=#{cid} and lab_id=#{labId}")
    int deleteById(Integer cid, Integer labId);
}
