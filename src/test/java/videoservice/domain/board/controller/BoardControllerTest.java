package videoservice.domain.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import videoservice.domain.board.dto.BoardUpdateRequest;
import videoservice.global.security.authentication.UserAccount;
import videoservice.global.security.jwt.JwtProcessor;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static videoservice.utility.ApiDocumentUtils.getRequestPreProcessor;
import static videoservice.utility.ApiDocumentUtils.getResponsePreProcessor;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtProcessor jwtProcessor;

    @Autowired
    private AccountRepository accountRepository;

    @Qualifier("objectMapper")
    @Autowired
    private ObjectMapper objectMapper;

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

        String content = objectMapper.writeValueAsString(boardAddRequest);

        // when
        ResultActions actions = mockMvc.perform(
                post("/boards")
                        .header("Authorization", jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content)
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

    @Test
    @DisplayName("게시물 상세 조회_성공")
    void boardDetails_Success() throws Exception {
        // given
        Long accountId = 10001L;
        Account account = accountRepository.findById(accountId).get();
        String jwt = "Bearer " + jwtProcessor.createAuthJwtToken(new UserAccount(account));

        Long boardId = 40001L;

        // when
        ResultActions actions = mockMvc.perform(
                get("/boards/{boardId}", boardId)
                        .header("Authorization", jwt)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.videoURL").isNotEmpty())
                .andExpect(jsonPath("$.title").isNotEmpty())
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andExpect(jsonPath("$.views").isNumber())
                .andExpect(jsonPath("$.breakPoints").isNumber())
                .andExpect(jsonPath("$.adURLs").isArray())
                .andExpect(jsonPath("$.adTimes").isArray())
                .andDo(document("boardDetails",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰").attributes(key("constraints").value("JWT Form")).optional()
                        ),
                        pathParameters(
                                parameterWithName("boardId").description("게시물 ID").attributes(key("constraints").value("NotNull"))
                        ),
                        responseFields(
                                fieldWithPath("videoURL").description("비디오 URL").attributes(key("constraints").value("NotNull")),
                                fieldWithPath("title").description("게시물 제목").attributes(key("constraints").value("NotBlank, Length(min=1, max=100)")),
                                fieldWithPath("content").description("게시물 내용").attributes(key("constraints").value("NotBlank, Length(min=1, max=300)")),
                                fieldWithPath("views").description("조회수").attributes(key("constraints").value("Number")),
                                fieldWithPath("breakPoints").description("영상 중단 지점").attributes(key("constraints").value("Number")),
                                fieldWithPath("adURLs").description("광고 URL 목록").attributes(key("constraints").value("Array")),
                                fieldWithPath("adTimes").description("광고 삽입 시점 목록").attributes(key("constraints").value("Array"))
                        )
                ));
    }

    @Test
    @DisplayName("게시물 수정_성공")
    void boardUpdate_Success() throws Exception {
        // given
        Long accountId = 10001L;
        Account account = accountRepository.findById(accountId).get();
        String jwt = "Bearer " + jwtProcessor.createAuthJwtToken(new UserAccount(account));

        Long boardId = 40001L;
        BoardUpdateRequest boardUpdateRequest = BoardUpdateRequest.builder()
                .title("Updated Title")
                .content("Updated Content")
                .build();

        String content = objectMapper.writeValueAsString(boardUpdateRequest);

        // when
        ResultActions actions = mockMvc.perform(
                put("/boards/{boardId}", boardId)
                        .header("Authorization", jwt)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andDo(document("boardUpdate",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰").attributes(key("constraints").value("JWT Form"))
                        ),
                        pathParameters(
                                parameterWithName("boardId").description("게시물 ID").attributes(key("constraints").value("NotNull"))
                        ),
                        requestFields(
                                fieldWithPath("title").description("게시물 제목").attributes(key("constraints").value("NotBlank, Length(min=1, max=100)")).optional(),
                                fieldWithPath("content").description("게시물 내용").attributes(key("constraints").value("NotBlank, Length(min=1, max=300)")).optional()
                        ),
                        responseFields(
                                fieldWithPath("id").description("수정된 게시물의 ID")
                        )
                ));
    }

    @Test
    @DisplayName("게시물 삭제_성공")
    void deleteBoard_Success() throws Exception {
        // given
        Long accountId = 10001L;
        Account account = accountRepository.findById(accountId).get();
        String jwt = "Bearer " + jwtProcessor.createAuthJwtToken(new UserAccount(account));

        Long boardId = 40001L;

        // when
        ResultActions actions = mockMvc.perform(
                delete("/boards/{boardId}", boardId)
                        .header("Authorization", jwt)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andDo(document("deleteBoard",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT 토큰").attributes(key("constraints").value("JWT Form"))
                        ),
                        pathParameters(
                                parameterWithName("boardId").description("게시물 ID").attributes(key("constraints").value("NotNull"))
                        )
                ));
    }
}