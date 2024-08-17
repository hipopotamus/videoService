package videoservice.domain.watchHistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import videoservice.domain.watchHistory.entity.WatchHistory;

import java.util.Optional;

public interface WatchHistoryRepository extends JpaRepository<WatchHistory, Long> {

    @Query("select watchHistory from WatchHistory watchHistory " +
            "where watchHistory.account.id = :accountId " +
            "and watchHistory.board.id = :boardId " +
            "and watchHistory.deleted = false")
    Optional<WatchHistory> findByAccountAndBoard(@Param("accountId") Long accountId, @Param("boardId") Long boardId);

}
