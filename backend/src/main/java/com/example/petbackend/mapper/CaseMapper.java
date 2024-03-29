package com.example.petbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.petbackend.pojo.Illcase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CaseMapper extends BaseMapper<Illcase> {
    @Select("select * from illcase")
    List<Illcase> getAll();

    @Select("select * from illcase where ill_id in (select ill_id from ill where cate_id=#{cateId})")
    List<Illcase> selectByCate(Integer cateId);
}
