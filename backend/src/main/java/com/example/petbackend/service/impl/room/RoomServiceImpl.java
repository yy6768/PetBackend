package com.example.petbackend.service.impl.room;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.petbackend.mapper.RoomMapper;
import com.example.petbackend.pojo.Room;
import com.example.petbackend.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomMapper roomMapper;
    @Override
    public Map<String, String> getRoomByName(String room_name) {
       Map<String, String> results = new HashMap<>();
       if(room_name == null || room_name.isEmpty()) {
           results.put("error_message", "room_name can't be empty");
           return results;
       }
       QueryWrapper queryWrapper = new QueryWrapper();
       queryWrapper.eq("room_name", room_name);
       Room room = roomMapper.selectOne(queryWrapper);
       results.put("error_message", "success");
       results.put("room_num", room.getRoomNum());
       results.put("description", room.getDescription());
       return results;
    }
}
