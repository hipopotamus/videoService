package videoservice.domain.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import videoservice.domain.account.dto.AccountAddRequest;
import videoservice.domain.account.dto.AccountDetailResponse;
import videoservice.domain.account.entity.Account;
import videoservice.domain.account.repository.AccountRepository;
import videoservice.global.dto.IdDto;
import videoservice.global.exception.BusinessLogicException;
import videoservice.global.exception.ExceptionCode;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public IdDto addAccount(AccountAddRequest accountAddRequest) {

        verifyDuplicateEmail(accountAddRequest.getEmail());
        verifyDuplicateNickname(accountAddRequest.getNickname());

        String encodedPassword = bCryptPasswordEncoder.encode(accountAddRequest.getPassword());
        Account account = accountAddRequest.toAccount(encodedPassword);
        Account savedAccount = accountRepository.save(account);

        return new IdDto(savedAccount.getId());
    }

    public AccountDetailResponse findProfile(Long accountId) {

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_ACCOUNT));

        return AccountDetailResponse.of(account);
    }

    private void verifyDuplicateEmail(String email) {
        if (email != null) {
            if (accountRepository.existsByEmail(email)) {
                throw new BusinessLogicException(ExceptionCode.DUPLICATION_EMAIL);
            }
        }
    }

    private void verifyDuplicateNickname(String nicknmae) {
        if (nicknmae != null) {
            if (accountRepository.existsByNickname(nicknmae)) {
                throw new BusinessLogicException(ExceptionCode.DUPLICATION_NICKNAME);
            }
        }
    }
}
