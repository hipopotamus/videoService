package videoservice.domain.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
                post("/boards/{boardId}", boardId)
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

    @Test
    @DisplayName("조회수 증가_성공")
    void upViews_Success() throws Exception {
        // given
        Long boardId = 40001L;

        // when
        ResultActions actions = mockMvc.perform(
                post("/boards/statistic/views/{boardId}", boardId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andDo(document("upViews",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("boardId").description("게시물 ID").attributes(key("constraints").value("NotNull"))
                        )
                ));
    }

    @Test
    @DisplayName("재생시간 추가_성공")
    void totalPlaytimeAdd_Success() throws Exception {
        // given
        Long boardId = 40001L;
        Long playtime = 300L;

        // when
        ResultActions actions = mockMvc.perform(
                put("/boards/statistic/totalPlaytime/{boardId}?playtime={playtime}", boardId, playtime)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andDo(document("totalPlaytimeAdd",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("boardId").description("게시물 ID").attributes(key("constraints").value("NotNull"))
                        ),
                        queryParameters(
                                parameterWithName("playtime").description("추가할 재생시간 (초)").attributes(key("constraints").value("Long"))
                        )
                ));
    }

    @Test
    @DisplayName("광고 조회수 증가_성공")
    void adViewsUp_Success() throws Exception {
        // given
        Long boardId = 40001L;

        // when
        ResultActions actions = mockMvc.perform(
                post("/boards/statistic/adViews/{boardId}", boardId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andDo(document("adViewsUp",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("boardId").description("게시물 ID").attributes(key("constraints").value("NotNull"))
                        )
                ));
    }
}