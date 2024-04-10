package com.example.petbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.petbackend.pojo.Ill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface IllMapper extends BaseMapper<Ill> {
    @Select("select * from ill where cate_id=#{cateId}")
    List<Ill> getAllInCate(String cateId);

    @Select("select * from ill where cate_id in (select cate_id from cate where cate_name=#{cateName})")
    List<Ill> selectByCate(String cateName);

    @Select("select * from ill where ill_name=#{illName}")
    List<Ill> selectByName(String illName);

}
