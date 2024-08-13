package videoservice.domain.account.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import videoservice.domain.account.dto.AccountAddRequest;
import videoservice.domain.account.dto.AccountDetailResponse;
import videoservice.domain.account.dto.AccountModifyRequest;
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

        Account account = getAccountForAdd(accountAddRequest);
        Account savedAccount = accountRepository.save(account);

        return new IdDto(savedAccount.getId());
    }

    public AccountDetailResponse findProfile(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_ACCOUNT));

        return AccountDetailResponse.of(account);
    }

    @Transactional
    public IdDto modifyAccount(Long accountId, AccountModifyRequest accountModifyRequest) {
        verifyDuplicateNickname(accountModifyRequest.getNickname());

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_ACCOUNT));

        Account modifyAccountParameter = getAccountForModify(accountModifyRequest);
        account.modify(modifyAccountParameter);

        return new IdDto(account.getId());
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

    private Account getAccountForAdd(AccountAddRequest accountAddRequest) {
        String encodedPassword = bCryptPasswordEncoder.encode(accountAddRequest.getPassword());

        return accountAddRequest.toAccount(encodedPassword);
    }

    private Account getAccountForModify(AccountModifyRequest accountModifyRequest) {
        String encodedPassword = null;
        if (accountModifyRequest.getPassword() != null) {
            encodedPassword = bCryptPasswordEncoder.encode(accountModifyRequest.getPassword());
        }

        return accountModifyRequest.toAccount(encodedPassword);
    }
}
