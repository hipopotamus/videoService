package videoservice.domain.account.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import videoservice.domain.account.dto.AccountIdResponse;
import videoservice.domain.account.entity.QAccount;

import java.util.List;

@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<AccountIdResponse> accountIdPageByCursor(Long lastAccountId, int limit) {
        QAccount account = QAccount.account;

        return jpaQueryFactory
                .select(Projections.constructor(AccountIdResponse.class,
                        account.id))
                .from(account)
                .where(account.id.gt(lastAccountId)) // board.id > lastBoardId
                .orderBy(account.id.asc())         // board.id 오름차순 정렬
                .limit(limit)
                .fetch();
    }
}
