package videoservice.domain.board.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import videoservice.domain.account.entity.Account;
import videoservice.domain.account.repository.AccountRepository;
import videoservice.domain.board.dto.BoardAddRequest;
import videoservice.global.security.authentication.UserAccount;
import videoservice.global.security.jwt.JwtProcessor;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static videoservice.utility.ApiDocumentUtils.getRequestPreProcessor;
import static videoservice.utility.ApiDocumentUtils.getResponsePreProcessor;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtProcessor jwtProcessor;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private Gson gson;

    @Test
    @DisplayName("게시물 추가_성공")
    void boardAdd_Success() throws Exception {
        // given
        Long accountId = 10001L;
        Account account = accountRepository.findById(accountId).get();
        String jwt = "Bearer " + jwtProcessor.createAuthJwtToken(new UserAccount(account));

        BoardAddRequest boardAddRequest = BoardAddRequest.builder()
                .videoId(30001L)
                .title("Test Title")
                .content("Test Content")
                .build();

        String content = gson.toJson(boardAddRequest);

        // when
        ResultActions actions = mockMvc.perform(
                post("/boards")
                        .header("Authorization", jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isCreated())
                .andDo(document("boardAdd",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰").attributes(key("constraints").value("JWT Form"))
                        ),
                        requestFields(
                                fieldWithPath("videoId").description("영상 ID").attributes(key("constraints").value("NotNull")),
                                fieldWithPath("title").description("게시물 제목").attributes(key("constraints").value("NotBlank, Length(min=1, max=100)")),
                                fieldWithPath("content").description("게시물 내용").attributes(key("constraints").value("NotBlank, Length(min=1, max=300)"))
                        ),
                        responseFields(
                                fieldWithPath("id").description("생성된 게시물의 ID")
                        )
                ));
    }
}