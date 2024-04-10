package com.example.petbackend.service.room;

import java.util.Map;

public interface RoomService {
    Map<String, String> getRoomByName(String room_name);
}
