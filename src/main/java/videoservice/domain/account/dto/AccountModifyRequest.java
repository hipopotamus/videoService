package videoservice.domain.account.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import videoservice.domain.account.entity.Account;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountModifyRequest {

    @NotBlank
    @Length(min = 8, max = 30)
    private String nickname;

    @NotBlank
    @Length(min = 2, max = 20)
    private String password;

    public Account toAccount(String encodedPassword) {
        return Account.builder()
                .nickname(nickname)
                .password(encodedPassword)
                .build();
    }
}
