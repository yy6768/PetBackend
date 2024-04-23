package com.example.petbackend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.petbackend.pojo.Anchor;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AnchorMapper extends BaseMapper<Anchor> {
    // 这里可以添加其他自定义的数据库操作方法
}
