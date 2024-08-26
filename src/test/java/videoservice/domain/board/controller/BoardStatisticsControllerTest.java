package videoservice.domain.board.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import videoservice.domain.account.repository.AccountRepository;
import videoservice.global.security.jwt.JwtProcessor;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
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
public class BoardStatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtProcessor jwtProcessor;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("조회수 증가_성공")
    void viewsUp_Success() throws Exception {
        // given
        Long boardId = 40001L;

        // when
        ResultActions actions = mockMvc.perform(
                post("/boards/statistics/views/{boardId}", boardId)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andDo(document("viewsUp",
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
                post("/boards/statistics/totalPlaytime/{boardId}?playtime={playtime}", boardId, playtime)
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
                                parameterWithName("playtime").description("추가할 재생시간 (초)").attributes(key("constraints").value("none"))
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
                post("/boards/statistics/adViews/{boardId}", boardId)
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

    @Test
    @DisplayName("게시물 통계 목록 조회_성공")
    void boardStatisticsList_Success() throws Exception {
        // given
        Pageable pageable = PageRequest.of(0, 20, Sort.by("createdAt").descending());

        // when
        ResultActions actions = mockMvc.perform(
                get("/boards/statistics")
                        .param("page", String.valueOf(pageable.getPageNumber()))
                        .param("size", String.valueOf(pageable.getPageSize()))
                        .param("sort", "createdAt,desc")
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalPages").isNumber())
                .andExpect(jsonPath("$.totalElements").isNumber())
                .andExpect(jsonPath("$.first").isBoolean())
                .andExpect(jsonPath("$.last").isBoolean())
                .andExpect(jsonPath("$.size").value(pageable.getPageSize()))
                .andDo(document("boardStatisticList",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        queryParameters(
                                parameterWithName("page").description("페이지 번호").attributes(key("constraints").value("Default=0")).optional(),
                                parameterWithName("size").description("페이지 크기").attributes(key("constraints").value("Default=20")).optional(),
                                parameterWithName("sort").description("정렬 기준").attributes(key("constraints").value("Default=createdAt,desc")).optional()
                        ),
                        responseFields(
                                fieldWithPath("content[]").description("게시물 통계 리스트"),
                                fieldWithPath("content[].accountId").description("게시물 작성자 ID"),
                                fieldWithPath("content[].boardId").description("게시물 ID"),
                                fieldWithPath("content[].views").description("조회수"),
                                fieldWithPath("content[].adViews").description("광고 조회수"),
                                fieldWithPath("content[].totalPlaytime").description("총 재생 시간"),
                                fieldWithPath("totalPages").description("전체 페이지 수"),
                                fieldWithPath("totalElements").description("전체 요소 수"),
                                fieldWithPath("first").description("첫 번째 페이지 여부"),
                                fieldWithPath("last").description("마지막 페이지 여부"),
                                fieldWithPath("size").description("페이지 크기"),
                                fieldWithPath("pageNumber").description("현재 페이지 번호"),
                                fieldWithPath("numberOfElements").description("현재 페이지의 요소 수"),
                                fieldWithPath("sorted").description("정렬 여부")
                        )
                ));
    }
}
