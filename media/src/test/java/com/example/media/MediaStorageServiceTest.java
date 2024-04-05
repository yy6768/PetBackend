package com.example.media;

import com.example.media.controller.CaseFileController;
import com.example.media.service.file.FileStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CaseFileController.class)
public class MediaStorageServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private FileStorageService fileStorageService;
    @BeforeEach
    public void setup() {
        // 初始化MockMvc对象
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testUploadFile() throws Exception {
        // 创建模拟的文件
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "filename.txt",
                "text/plain",
                "This is the file content".getBytes()
        );

        // 模拟service的响应
        Map<String, String> serviceResponse = new HashMap<>();
        serviceResponse.put("url", "http://example.com/filename.txt");
        serviceResponse.put("error_message", "success");
        given(fileStorageService.uploadFile(mockFile)).willReturn(serviceResponse);

        // 执行文件上传的测试请求，并验证结果
        mockMvc.perform(multipart("/api/case/upload").file(mockFile))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
