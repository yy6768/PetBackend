package com.example.petbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.petbackend.pojo.Cate;
import com.example.petbackend.pojo.Lab;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LabMapper extends BaseMapper<Lab> {
    @Select("select * from lab")
    List<Lab> getAll();
}
