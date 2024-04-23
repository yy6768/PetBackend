package com.example.petbackend.service.room;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface AnchorService {

    JSONObject getAnchorByRoomId(Integer roomName);

    Map<String, String> createAnchor(Integer roomName, Float anchorX, Float anchorY, Float anchorZ, String desc, String imageUrl, String videoUrl);

    Map<String, String> deleteAnchor(Integer anchorId);

}
