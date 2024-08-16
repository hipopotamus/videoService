package videoservice.domain.adVideo.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import videoservice.domain.account.entity.Account;
import videoservice.domain.account.repository.AccountRepository;
import videoservice.global.security.authentication.UserAccount;
import videoservice.global.security.jwt.JwtProcessor;

import java.io.File;
import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static videoservice.utility.ApiDocumentUtils.getRequestPreProcessor;
import static videoservice.utility.ApiDocumentUtils.getResponsePreProcessor;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class AdVideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtProcessor jwtProcessor;

    @Test
    @DisplayName("광고 영상 업로드_성공")
    void adVideoAdd_Success() throws Exception {
        // given
        Long accountId = 10001L;
        Account account = accountRepository.findById(accountId).get();
        String jwt = "Bearer " + jwtProcessor.createAuthJwtToken(new UserAccount(account));

        MockMultipartFile adVideo = new MockMultipartFile("adVideo", "test.mp4",
                "video/mp4", "test adVideo content".getBytes());

        // when
        ResultActions actions = mockMvc.perform(
                multipart("/adVideos")
                        .file(adVideo)
                        .param("advertiser", "testAdvertiser")
                        .param("weight", "10000")
                        .header("Authorization", jwt)
        );

        // then
        actions
                .andExpect(status().isCreated())
                .andDo(document("adVideoAdd",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                List.of(
                                        headerWithName("Authorization").description("JWT").attributes(key("constraints").value("JWT Form"))
                                )
                        ),
                        requestParts(
                                partWithName("adVideo").description("업로드할 광고 영상 파일").attributes(key("constraints").value("NotNull"))
                        ),
                        responseFields(
                                fieldWithPath("id").description("생성된 광고 영상의 ID")
                        )
                ));

        deleteFilesInDirectory("/Users/hipo/Desktop/project/toy_project/video/videoservice/src/test/resources/video");
    }

    public static void deleteFilesInDirectory(String directoryPath) {
        File directory = new File(directoryPath);

        if (!directory.exists()) {
            System.out.println("Directory does not exist.");
            return;
        }

        if (!directory.isDirectory()) {
            System.out.println("Provided path is not a directory.");
            return;
        }

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    boolean deleted = file.delete();
                    if (!deleted) {
                        System.out.println("Failed to delete file: " + file.getName());
                    }
                } else if (file.isDirectory()) {
                    deleteFilesInDirectory(file.getAbsolutePath()); // Recursive delete for subdirectories
                }
            }
        }
    }
}