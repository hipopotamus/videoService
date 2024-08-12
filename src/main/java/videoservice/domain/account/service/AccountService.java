package videoservice.domain.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import videoservice.domain.account.dto.AccountAddRequest;
import videoservice.domain.account.entity.Account;
import videoservice.domain.account.repository.AccountRepository;
import videoservice.global.dto.IdDto;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public IdDto addAccount(AccountAddRequest accountAddRequest) {

        String encodedPassword = bCryptPasswordEncoder.encode(accountAddRequest.getPassword());
        Account account = accountAddRequest.toAccount(encodedPassword);
        Account savedAccount = accountRepository.save(account);

        return new IdDto(savedAccount.getId());
    }
}
