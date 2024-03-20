package com.example.petbackend.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.petbackend.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
