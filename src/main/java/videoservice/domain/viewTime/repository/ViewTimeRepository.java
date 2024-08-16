package videoservice.domain.viewTime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import videoservice.domain.viewTime.entity.ViewTime;

import java.util.Optional;

public interface ViewTimeRepository extends JpaRepository<ViewTime, Long> {

    @Query("select viewTime from ViewTime viewTime " +
            "where viewTime.account.id = :accountId " +
            "and viewTime.board.id = :boardId and viewTime.deleted = false ")
    Optional<ViewTime> findByAccountIdAndBoardId(@Param("accountId") Long accountId, @Param("boardId") Long boardId);
}
