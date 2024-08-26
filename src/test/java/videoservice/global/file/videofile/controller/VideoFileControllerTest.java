package videoservice.global.file.videofile.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static videoservice.utility.ApiDocumentUtils.getRequestPreProcessor;
import static videoservice.utility.ApiDocumentUtils.getResponsePreProcessor;

@Transactional
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
                        ),
                        pathParameters(
                                parameterWithName("videoName").description("조회할 영상의 파일명").attributes(key("constraints").value("NotBlank"))
                        )
                ));

        // Cleanup: Delete the mock video file
        mockVideoFile.delete();
    }

    @Test
    @DisplayName("광고 영상 조회_성공")
    void adVideoDetails_Success() throws Exception {

        // given
        String adVideoName = "testVideo.mp4";
        byte[] mockVideoContent = createMockVideoContent();
        File mockVideoFile = new File(path + adVideoName);

        // Create a mock video file
        try (FileOutputStream fos = new FileOutputStream(mockVideoFile)) {
            fos.write(mockVideoContent);
        }

        // when
        ResultActions actions = mockMvc.perform(
                get("/videoFiles/progressive/{adVideoName}", adVideoName)
                        .accept(MediaType.APPLICATION_OCTET_STREAM)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andDo(document("videoProgressive",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("adVideoName").description("조회할 광고 영상의 파일명").attributes(key("constraints").value("NotBlank"))
                        )
                ));

        mockVideoFile.delete();
    }

    private byte[] createMockVideoContent() {
        // Create a byte array representing a mock video file content
        String mockContent = "This is a mock video content.";
        return mockContent.getBytes();
    }

}