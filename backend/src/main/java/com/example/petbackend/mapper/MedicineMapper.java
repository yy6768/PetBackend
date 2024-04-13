package com.example.petbackend.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.petbackend.pojo.Medicine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MedicineMapper extends BaseMapper<Medicine> {

    @Select("select * from medicine")
    List<Medicine> getAll();

//    @Sql("DELETE FROM user_detail WHERE user_id = #{id}; DELETE FROM user WHERE id = #{id};")
//    int deleteCaseMedById(@Param("id") Long id);

    int deleteCaseMedById(Integer id);

}

