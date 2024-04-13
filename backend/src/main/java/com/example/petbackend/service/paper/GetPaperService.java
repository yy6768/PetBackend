package com.example.petbackend.service.paper;

import java.util.Map;

public interface GetPaperService {
    //根据试卷id查询试卷
    Map<String, Object> getPaperById(Integer paper_id);
    //根据关键字搜索分页获取所有试卷
    Map<String, Object> getAllPaper(Integer page, Integer pageSize, String key, Integer type);


}
