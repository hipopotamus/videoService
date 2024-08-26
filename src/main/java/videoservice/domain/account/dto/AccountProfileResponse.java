package videoservice.domain.account.dto;

import lombok.Builder;
import lombok.Data;
import videoservice.domain.account.entity.Account;
import videoservice.domain.account.entity.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class AccountProfileResponse {

    private String email;

    private String nickname;

    private Gender gender;

    private LocalDate birthDate;

    private LocalDateTime createdAt;

    public static AccountProfileResponse of(Account account) {
        return AccountProfileResponse.builder()
                .email(account.getEmail())
                .nickname(account.getNickname())
                .gender(account.getGender())
                .birthDate(account.getBirthDate())
                .createdAt(account.getCreatedAt())
                .build();
    }
}
