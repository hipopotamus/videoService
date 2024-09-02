package videoservice.domain.account.repository;

import videoservice.domain.account.dto.AccountIdResponse;

import java.util.List;

public interface AccountRepositoryCustom {

    List<AccountIdResponse> accountIdPageByCursor(Long lastAccountId, int limit);
}
