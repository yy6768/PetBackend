package com.example.petbackend.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.petbackend.pojo.Ill;
import com.example.petbackend.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where username=#{username}")
    List<User> selectByName(String username);
}
