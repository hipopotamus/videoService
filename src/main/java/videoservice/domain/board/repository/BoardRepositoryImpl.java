package videoservice.domain.board.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import videoservice.domain.account.entity.QAccount;
import videoservice.domain.board.dto.BoardStatisticsListResponse;
import videoservice.domain.board.entity.QBoard;

import java.util.List;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<BoardStatisticsListResponse> boardPageWithAccountByCursor(Long lastBoardId, int limit) {

        QBoard board = QBoard.board;
        QAccount account = QAccount.account;

        return jpaQueryFactory
                .select(Projections.constructor(BoardStatisticsListResponse.class,
                        board.account.id,
                        board.id,
                        board.views,
                        board.adViews,
                        board.totalPlaytime))
                .from(board)
                .join(board.account, account)
                .where(board.id.gt(lastBoardId)) // board.id > lastBoardId
                .orderBy(board.id.asc())         // board.id 오름차순 정렬
                .limit(limit)
                .fetch();
    }
}
