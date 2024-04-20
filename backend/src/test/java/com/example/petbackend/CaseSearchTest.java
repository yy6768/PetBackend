package com.example.petbackend;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson2.JSON;
import com.example.petbackend.pojo.IllcaseDoc;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.example.petbackend.constants.CaseConstants.MAPPING_TEMPLATE;

public class CaseSearchTest {
    private RestHighLevelClient client;

    @Test
    void testMatchAll() throws IOException {
        SearchRequest request = new SearchRequest("illcase");
        request.source().query(QueryBuilders.matchAllQuery());
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits searchHits = response.getHits();
        long total = searchHits.getTotalHits().value;
        System.out.println("共搜索到"+total+"条数据");
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            IllcaseDoc illcaseDoc = JSON.parseObject(json, IllcaseDoc.class);
            System.out.println("illcaseDoc="+illcaseDoc);
        }
        System.out.println(response);
    }

    @Test
    void testMatch() throws IOException {
        SearchRequest request = new SearchRequest("illcase");
        request.source().query(QueryBuilders.matchQuery("all","皮肤"));
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        SearchHits searchHits = response.getHits();
        long total = searchHits.getTotalHits().value;
        System.out.println("共搜索到"+total+"条数据");
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            IllcaseDoc illcaseDoc = JSON.parseObject(json, IllcaseDoc.class);
            System.out.println("illcaseDoc="+illcaseDoc);
        }
        System.out.println(response);
    }

    @BeforeEach
    void setUp(){
        this.client=new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://124.223.161.233:9200")
        ));
    }

    @AfterEach
    void tearDown() throws IOException {
        this.client.close();
    }
}
