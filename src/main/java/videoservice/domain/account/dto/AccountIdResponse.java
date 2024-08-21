package videoservice.domain.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import videoservice.domain.account.entity.Account;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountIdResponse {

    private Long accountId;

    public static AccountIdResponse of(Account account) {
        return AccountIdResponse.builder()
                .accountId(account.getId())
                .build();
    }
}
