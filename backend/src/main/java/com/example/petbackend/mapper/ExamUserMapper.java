package com.example.petbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.petbackend.pojo.ExamUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExamUserMapper extends BaseMapper<ExamUser> {
}
