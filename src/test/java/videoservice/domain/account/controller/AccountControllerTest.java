package videoservice.domain.account.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import videoservice.domain.account.dto.AccountAddRequest;
import videoservice.domain.account.entity.Gender;
import videoservice.global.security.dto.LoginDto;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static videoservice.utility.ApiDocumentUtils.getRequestPreProcessor;
import static videoservice.utility.ApiDocumentUtils.getResponsePreProcessor;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Test
    @DisplayName("로그인_성공")
    void accountLogin_Success() throws Exception {

        //given
        String email = "test1@test.com";
        String password = "12345678";

        LoginDto loginDto = new LoginDto(email, password);
        String content = gson.toJson(loginDto);


        //when
        ResultActions actions = mockMvc.perform(
                post("/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );


        //then
        actions
                .andExpect(status().isOk())
                .andDo(document(
                        "login",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                List.of(
                                        fieldWithPath("email").description("이메일"),
                                        fieldWithPath("password").description("비밀번호")
                                )
                        ),
                        responseHeaders(
                                List.of(
                                        headerWithName("Authorization").description("JWT")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("계정 추가_성공")
    void accountAdd_Success() throws Exception {
        // given
        AccountAddRequest accountAddRequest = AccountAddRequest.builder()
                .email("test@test.com")
                .password("testNickname")
                .nickname("password123")
                .gender(Gender.MALE)
                .birthday("1993-12-22")
                .build();

        String content = gson.toJson(accountAddRequest);

        // when
        ResultActions actions = mockMvc.perform(
                post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andDo(document("accountAdd",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(
                                fieldWithPath("email").description("이메일 주소").attributes(key("constraints").value("EmailForm")),
                                fieldWithPath("password").description("비밀번호").attributes(key("constraints").value("NotBlank, Length(min=8, max=30)")),
                                fieldWithPath("nickname").description("닉네임").attributes(key("constraints").value("NotBlank, Length(min=2, max=20)")),
                                fieldWithPath("gender").description("성별").attributes(key("constraints").value("NotNull")),
                                fieldWithPath("birthday").description("생년월일").attributes(key("constraints").value("NotNull"))
                        ),
                        responseFields(
                                fieldWithPath("id").description("생성된 계정의 ID")
                        )
                ));
    }
}