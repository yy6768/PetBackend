package com.example.petbackend;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.example.petbackend.constants.CaseConstants.MAPPING_TEMPLATE;

public class CaseIndexTest {
    private RestHighLevelClient client;

    @Test
    void testInit(){
        System.out.println(client);
    }

    @Test
    void createCaseIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("illcase");
        request.source(MAPPING_TEMPLATE, XContentType.JSON);
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    @Test
    void testDeleteCaseIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("illcase");
        client.indices().delete(request, RequestOptions.DEFAULT);
    }

    @Test
    void testExistsCaseIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("illcase");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.err.println(exists ? "索引库已经存在！" : "索引库不存在！");
    }

    @BeforeEach
    void setUp(){
        this.client=new RestHighLevelClient(RestClient.builder(
                HttpHost.create("http://localhost:9200")
        ));
    }

    @AfterEach
    void tearDown() throws IOException {
        this.client.close();
    }
}
