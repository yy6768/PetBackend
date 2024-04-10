package com.example.petbackend.controller.room;

import com.example.petbackend.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/room/get")
    public Map<String, String> getRoomByName(@RequestParam  Map<String,String> map) {
        String name = map.get("room_name");
        return roomService.getRoomByName(name);
    }
}
