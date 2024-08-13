package videoservice.domain.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import videoservice.domain.account.entity.Account;
import videoservice.domain.account.entity.Gender;
import videoservice.domain.account.entity.Role;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class AccountAddRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Length(min = 8, max = 30)
    private String password;

    @NotBlank
    @Length(min = 2, max = 20)
    private String nickname;

    @NotNull
    private Gender gender;

    @NotNull
    private String birthday;

    public Account toAccount(String encodedPassword) {
        return Account.builder()
                .email(email)
                .password(encodedPassword)
                .nickname(nickname)
                .gender(gender)
                .birthDate(LocalDate.parse(birthday, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .role(Role.USER)
                .build();
    }
}
