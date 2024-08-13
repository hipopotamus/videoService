package videoservice.domain.account.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import videoservice.global.auditing.BaseEntity;

import java.time.LocalDate;
import java.util.Optional;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    private String email;

    private String password;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    public void modify(Account account) {
        Optional.ofNullable(account.getNickname()).ifPresent(nickname -> this.nickname = nickname);
        Optional.ofNullable(account.getPassword()).ifPresent(password -> this.password = password);
    }
}
