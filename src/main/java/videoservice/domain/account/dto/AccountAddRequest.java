package videoservice.domain.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import videoservice.domain.account.entity.Account;
import videoservice.domain.account.entity.Gender;
import videoservice.domain.account.entity.Role;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private LocalDate birthday;

    public Account toAccount(String encodedPassword) {
        return Account.builder()
                .email(email)
                .password(encodedPassword)
                .nickname(nickname)
                .gender(gender)
                .birthDate(birthday)
                .role(Role.USER)
                .build();
    }
}
