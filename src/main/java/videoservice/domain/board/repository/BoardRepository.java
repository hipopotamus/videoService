package videoservice.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import videoservice.domain.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Modifying
    @Query("update Board board " +
            "set board.totalPlaytime = board.totalPlaytime + :playtime " +
            "where board.id = :boardId and board.deleted = false ")
    void addPlaytime(@Param("playtime") Long playtime, @Param("boardId") Long boardId);
}
