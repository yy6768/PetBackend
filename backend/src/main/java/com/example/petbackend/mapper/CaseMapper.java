package com.example.petbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.petbackend.pojo.Illcase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

@Mapper
public interface CaseMapper extends BaseMapper<Illcase> {
    @Select("select * from illcase")
    List<Illcase> getAll();

    @Select("select * from illcase where ill_id in (select ill_id from ill where cate_id in(select cate_id from cate where cate_name=#{cateName}))")
    List<Illcase> selectByCate(String cateName);

    @Select("select * from illcase where ill_id in (select ill_id from ill where ill_name=#{illName})")
    List<Illcase> selectByIll(String illName);

    @Select("select * from illcase where date=#{date}")
    List<Illcase> selectByDate(Date date);

    @Select("select * from illcase order by cid")
    List<Illcase> sortById();

    @Select("select * from illcase join user u on illcase.uid = u.uid order by username")
    List<Illcase> sortByDoctor();

    @Select("select * from illcase order by date")
    List<Illcase> sortByDate();


}
