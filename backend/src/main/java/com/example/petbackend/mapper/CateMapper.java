package com.example.petbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.petbackend.pojo.Cate;
import com.example.petbackend.pojo.Illcase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CateMapper extends BaseMapper<Cate> {
    @Select("select * from cate")
    List<Cate> getAll();
}
