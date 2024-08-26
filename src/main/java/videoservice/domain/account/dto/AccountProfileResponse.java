package videoservice.domain.account.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd::HH:mm:ss")
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
