package videoservice.domain.account.controller;

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
import videoservice.domain.account.dto.AccountAddRequest;
import videoservice.domain.account.dto.AccountModifyRequest;
import videoservice.domain.account.entity.Account;
import videoservice.domain.account.entity.Gender;
import videoservice.domain.account.repository.AccountRepository;
import videoservice.global.security.authentication.UserAccount;
import videoservice.global.security.dto.LoginDto;
import videoservice.global.security.jwt.JwtProcessor;

import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
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

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtProcessor jwtProcessor;

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
                                        fieldWithPath("email").description("이메일").attributes(key("constraints").value("none")),
                                        fieldWithPath("password").description("비밀번호").attributes(key("constraints").value("none"))
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
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
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

    @Test
    @DisplayName("프로필 상세 정보 조회_성공")
    void profileDetails_Success() throws Exception {
        // given
        Long accountId = 10001L;
        Account account = accountRepository.findById(accountId).get();
        String jwt = "Bearer " + jwtProcessor.createAuthJwtToken(new UserAccount(account));

        // when
        ResultActions actions = mockMvc.perform(
                get("/accounts")
                        .header("Authorization", jwt)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").isNotEmpty())
                .andExpect(jsonPath("$.nickname").isNotEmpty())
                .andExpect(jsonPath("$.gender").isNotEmpty())
                .andExpect(jsonPath("$.birthDate").isNotEmpty())
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andDo(document("profileDetails",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                List.of(
                                        headerWithName("Authorization").description("JWT").attributes(key("constraints").value("JWT Form"))
                                )
                        ),
                        responseFields(
                                fieldWithPath("email").description("이메일 주소"),
                                fieldWithPath("nickname").description("닉네임"),
                                fieldWithPath("gender").description("성별"),
                                fieldWithPath("birthDate").description("생년월일"),
                                fieldWithPath("createdAt").description("계정 생성 일시")
                        )
                ));
    }

    @Test
    @DisplayName("계정 수정_성공")
    void accountModify_Success() throws Exception {
        // given
        Long accountId = 10001L;
        Account account = accountRepository.findById(accountId).get();
        String jwt = "Bearer " + jwtProcessor.createAuthJwtToken(new UserAccount(account));

        AccountModifyRequest accountModifyRequest = AccountModifyRequest.builder()
                .nickname("newNickname")
                .password("newPassword123")
                .build();

        String content = gson.toJson(accountModifyRequest);

        // when
        ResultActions actions = mockMvc.perform(
                put("/accounts")
                        .header("Authorization", jwt)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andDo(document("accountModify",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT").attributes(key("constraints").value("JWT Form"))
                        ),
                        requestFields(
                                fieldWithPath("nickname").description("새 닉네임").attributes(key("constraints").value("NotBlank, Length(min=2, max=20)")).optional(),
                                fieldWithPath("password").description("새 비밀번호").attributes(key("constraints").value("NotBlank, Length(min=8, max=30)")).optional()
                        ),
                        responseFields(
                                fieldWithPath("id").description("수정된 계정의 ID")
                        )
                ));
    }

    @Test
    @DisplayName("계정 삭제_성공")
    void accountDelete_Success() throws Exception {
        // given
        Long accountId = 10001L;
        Account account = accountRepository.findById(accountId).get();
        String jwt = "Bearer " + jwtProcessor.createAuthJwtToken(new UserAccount(account));

        // when
        ResultActions actions = mockMvc.perform(
                delete("/accounts")
                        .header("Authorization", jwt)
        );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(content().string("Account deleted"))
                .andDo(document("accountDelete",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestHeaders(
                                headerWithName("Authorization").description("JWT").attributes(key("constraints").value("JWT Form"))
                        )
                ));
    }
}