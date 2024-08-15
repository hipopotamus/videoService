package videoservice.domain.video.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static videoservice.utility.ApiDocumentUtils.getRequestPreProcessor;
import static videoservice.utility.ApiDocumentUtils.getResponsePreProcessor;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class VideoFileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String path = "/Users/hipo/Desktop/project/toy_project/video/videoservice/src/test/resources/video/";

    @Test
    @DisplayName("비디오 스트리밍_성공")
    void videoStream_Success() throws Exception {
        // given
        String videoName = "testVideo.mp4";
        byte[] mockVideoContent = createMockVideoContent();
        File mockVideoFile = new File(path + videoName);

        // Create a mock video file
        try (FileOutputStream fos = new FileOutputStream(mockVideoFile)) {
            fos.write(mockVideoContent);
        }

        // when
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders.get("/videoFiles/stream/{videoName}", videoName)
                        .header(HttpHeaders.RANGE, "bytes=0-1024")
        );

        // then
        actions
                .andExpect(status().isPartialContent())
                .andExpect(header().exists(HttpHeaders.CONTENT_TYPE))
                .andExpect(header().exists(HttpHeaders.CONTENT_RANGE))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, "video/mp4"))
                .andDo(document("videoStream",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName(HttpHeaders.RANGE).description("요청하는 바이트 범위").attributes(key("constraints").value("none"))
                        )
                ));

        // Cleanup: Delete the mock video file
        mockVideoFile.delete();
    }

    private byte[] createMockVideoContent() {
        // Create a byte array representing a mock video file content
        String mockContent = "This is a mock video content.";
        return mockContent.getBytes();
    }

}