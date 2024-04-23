package com.example.petbackend.service.impl.room;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.petbackend.mapper.AnchorMapper;
import com.example.petbackend.pojo.Anchor;
import com.example.petbackend.service.room.AnchorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnchorServiceImpl implements AnchorService {
    @Autowired
    private AnchorMapper anchorMapper;

    @Override
    public JSONObject getAnchorByRoomId(Integer roomId) {
        JSONObject results = new JSONObject();
        QueryWrapper<Anchor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_id", roomId);
        List<Anchor> anchors = anchorMapper.selectList(queryWrapper);
        results.put("error_message", "success");
        results.put("anchors", anchors);
        return results;
    }

    @Override
    public Map<String, String> createAnchor(Integer roomId, Float anchorX, Float anchorY, Float anchorZ, String desc, String imageUrl, String videoUrl) {
        Map<String, String> results = new HashMap<>();
        if (roomId == null || anchorX == null || anchorY == null || anchorZ == null || desc == null || desc.trim().isEmpty()) {
            results.put("error_message", "请补全锚点信息");
            return results;
        }
        Anchor anchor = new Anchor(null, roomId, anchorX, anchorY, anchorZ, desc, imageUrl, videoUrl);
        anchorMapper.insert(anchor);
        results.put("error_message", "success");

        return results;
    }

    @Override
    public Map<String, String> deleteAnchor(Integer anchorId) {
        Map<String, String> results = new HashMap<>();
        int res = anchorMapper.deleteById(anchorId);
        if (res < 1) {
            results.put("error_message", "删除锚点失败");
        } else {
            results.put("error_message", "success");
        }
        return results;
    }
}
