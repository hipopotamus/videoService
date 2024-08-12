package videoservice.domain.account.dto;

import lombok.Data;
import videoservice.domain.account.entity.Account;
import videoservice.domain.account.entity.Role;

@Data
public class AccountAddRequest {

    private String email;

    private String password;

    public Account toAccount(String encodedPassword) {
        return Account.builder()
                .email(email)
                .password(encodedPassword)
                .role(Role.USER)
                .build();
    }
}
