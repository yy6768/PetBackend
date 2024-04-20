package com.example.petbackend;

import com.alibaba.fastjson.JSON;
import com.example.petbackend.mapper.CaseMapper;
import com.example.petbackend.mapper.CateMapper;
import com.example.petbackend.mapper.IllMapper;
import com.example.petbackend.mapper.UserMapper;
import com.example.petbackend.pojo.Illcase;
import com.example.petbackend.pojo.IllcaseDoc;
import com.example.petbackend.service.illcase.CaseService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.io.IOException;
import java.util.List;

import static com.example.petbackend.constants.CaseConstants.MAPPING_TEMPLATE;


@SpringBootTest
public class CaseDocumentTest {
    @Autowired
    private CaseMapper caseMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CateMapper cateMapper;
    @Autowired
    private IllMapper illMapper;
    @MockBean
    private ServerEndpointExporter serverEndpointExporter;

    private RestHighLevelClient client;


    @Test
    void testAddDocument() throws IOException {
        Illcase illcase=caseMapper.selectById(13);

        IllcaseDoc illcaseDoc = new IllcaseDoc();
        illcaseDoc.setCid(illcase.getCid());
        illcaseDoc.setUsername(userMapper.selectById(illcase.getUid()).getUsername());
        illcaseDoc.setCateName(cateMapper.selectById(illMapper.selectById(illcase.getIllId()).getCateId()).getCateName());
        illcaseDoc.setIllName(illMapper.selectById(illcase.getIllId()).getIllName());
        illcaseDoc.setDate(illcase.getDate().toString());
        illcaseDoc.setResult(illcase.getResult());
        illcaseDoc.setTherapy(illcase.getTherapy());
        illcaseDoc.setBasicSituation(illcase.getBasicSituation());

        IndexRequest request = new IndexRequest("illcase").id(illcase.getCid().toString());
        request.source(JSON.toJSONString(illcaseDoc),XContentType.JSON);
        client.index(request,RequestOptions.DEFAULT);
    }

    @Test
    void testGetDocumentById() throws IOException {
        GetRequest request = new GetRequest("illcase", "13");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        String json = response.getSourceAsString();
        IllcaseDoc illcaseDoc = JSON.parseObject(json, IllcaseDoc.class);
        System.out.println(illcaseDoc);
    }

    @Test
    void testUpdateDocument() throws IOException {
        UpdateRequest request = new UpdateRequest("illcase", "13");
        request.doc(
                "result","1111",
                "therapy","2222"
        );
        client.update(request,RequestOptions.DEFAULT);
    }

    @Test
    void testDeleteDocument() throws IOException {
        DeleteRequest request = new DeleteRequest("illcase", "61");
        client.delete(request,RequestOptions.DEFAULT);
    }

    @Test
    void testBulkRequest() throws IOException {
        List<Illcase> illcases = caseMapper.getAll();
        System.out.println(illcases.size());
        BulkRequest request = new BulkRequest();
        for (Illcase illcase : illcases) {
            IllcaseDoc illcaseDoc = new IllcaseDoc();
            illcaseDoc.setCid(illcase.getCid());
            illcaseDoc.setUsername(userMapper.selectById(illcase.getUid()).getUsername());
            illcaseDoc.setCateName(cateMapper.selectById(illMapper.selectById(illcase.getIllId()).getCateId()).getCateName());
            illcaseDoc.setIllName(illMapper.selectById(illcase.getIllId()).getIllName());
            illcaseDoc.setDate(illcase.getDate().toString());
            illcaseDoc.setResult(illcase.getResult());
            illcaseDoc.setTherapy(illcase.getTherapy());
            illcaseDoc.setBasicSituation(illcase.getBasicSituation());
            request.add(new IndexRequest("illcase")
                    .id(illcaseDoc.getCid().toString())
                    .source(JSON.toJSONString(illcaseDoc),XContentType.JSON));
        }
        BulkResponse bulkResponse =  client.bulk(request,RequestOptions.DEFAULT);
        System.out.println(bulkResponse.buildFailureMessage());
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
