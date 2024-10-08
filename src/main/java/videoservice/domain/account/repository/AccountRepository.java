package videoservice.domain.account.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import videoservice.domain.account.entity.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>, AccountRepositoryCustom {

    @Query("select account from Account account where account.email = :email and account.deleted = false")
    Optional<Account> findByEmail(@Param("email") String email);

    @Query("select account from Account account where account.id = :id and account.deleted = false")
    Optional<Account> findById(@Param("id") Long id);

    @Query("select count(account) > 0 from Account account where account.email = :email and account.deleted = false")
    boolean existsByEmail(@Param("email") String email);

    @Query("select count(account) > 0 from Account account where account.nickname = :nickname and account.deleted = false")
    boolean existsByNickname(@Param("nickname") String nickname);

    @Query("select account from Account account where account.deleted = false")
    Page<Account> accountPage(Pageable pageable);
}
