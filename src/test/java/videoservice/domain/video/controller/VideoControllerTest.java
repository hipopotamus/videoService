package videoservice.domain.video.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import videoservice.domain.account.entity.Account;
import videoservice.domain.account.repository.AccountRepository;
import videoservice.global.videoUtility.VideoUtility;
import videoservice.global.security.authentication.UserAccount;
import videoservice.global.security.jwt.JwtProcessor;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static videoservice.utility.ApiDocumentUtils.getRequestPreProcessor;
import static videoservice.utility.ApiDocumentUtils.getResponsePreProcessor;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @MockBean
    private VideoUtility videoUtility;

    @Autowired
    private JwtProcessor jwtProcessor;

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

    @Test
    @DisplayName("비디오 업로드_성공")
    void videoAdd_Success() throws Exception {
        // given
        Long accountId = 10001L;
        Account account = accountRepository.findById(accountId).get();
        String jwt = "Bearer " + jwtProcessor.createAuthJwtToken(new UserAccount(account));

        MockMultipartFile video = new MockMultipartFile("video", "test.mp4",
                "video/mp4", "test video content".getBytes());

        Mockito.when(videoUtility.getVideoLength(Mockito.anyString(), Mockito.anyString())).thenReturn(123L);

        // when
        ResultActions actions = mockMvc.perform(
                multipart("/videos")
                        .file(video)
                        .header("Authorization", jwt)
        );

        // then
        actions
                .andExpect(status().isCreated())
                .andDo(document("videoAdd",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                List.of(
                                        headerWithName("Authorization").description("JWT").attributes(key("constraints").value("JWT Form"))
                                )
                        ),
                        requestParts(
                                partWithName("video").description("업로드할 비디오 파일").attributes(key("constraints").value("NotNull"))
                        ),
                        responseFields(
                                fieldWithPath("id").description("생성된 비디오의 ID")
                        )
                ));
    }


    private byte[] createMockVideoContent() {
        // Create a byte array representing a mock video file content
        String mockContent = "This is a mock video content.";
        return mockContent.getBytes();
    }

}