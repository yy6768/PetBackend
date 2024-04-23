package com.example.petbackend.controller.room;


import com.alibaba.fastjson.JSONObject;
import com.example.petbackend.service.room.AnchorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AnchorController {

    @Autowired
    private AnchorService anchorService;

    @PostMapping("/anchor/add")
    public Map<String, String> addAnchor(@RequestBody Map<String, String> info) {
        Integer roomId = Integer.parseInt(info.get("roomId"));
        Float anchorX = Float.parseFloat(info.get("anchorX"));
        Float anchorY = Float.parseFloat(info.get("anchorY"));
        Float anchorZ = Float.parseFloat(info.get("anchorZ"));
        String desc = info.get("anchorDesc");
        String imageUrl = info.get("imageUrl");
        String videoUrl = info.get("videoUrl");

        return anchorService.createAnchor(roomId, anchorX, anchorY, anchorZ, desc, imageUrl, videoUrl);
    }

    @GetMapping("/anchor/get")
    public JSONObject getAnchors(@RequestParam Map<String, String> info) {
        Integer roomId = Integer.parseInt(info.get("room_id"));
        return anchorService.getAnchorByRoomId(roomId);
    }

    @PostMapping("/anchor/delete")
    public Map<String, String> deleteAnchor(@RequestParam Map<String, String> info) {
        Integer anchorId = Integer.getInteger(info.get("anchorId"));
        return anchorService.deleteAnchor(anchorId);
    }
}
