package videoservice.domain.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import videoservice.domain.account.entity.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
