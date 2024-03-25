package com.example.petbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.petbackend.pojo.Lab;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LabMapper extends BaseMapper<Lab> {

    @Insert("insert into lab (lab_id, lab_name, lab_cost) " +
            "values " +
            "(#{labId},#{labName},#{labCost})")
    int insert(Lab lab);

    void delete(Lab lab);

    void update(Lab lab);

    @Select("select * from lab where lab_id=#{lab_id}")
    Lab getById(Integer id);
}
