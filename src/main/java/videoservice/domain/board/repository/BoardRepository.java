package videoservice.domain.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import videoservice.domain.board.entity.Board;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select board from Board board " +
            "where board.id = :boardId and board.deleted = false ")
    Optional<Board> findById(@Param("boardId") Long boardId);

    @Query("select board from Board board join fetch board.account " +
            "where board.id = :boardId and board.deleted = false")
    Optional<Board> findByIdWithAccount(@Param("boardId") Long boardId);

    @Query("select board from Board board join fetch board.account where board.deleted = false ")
    Page<Board> findStatisticList(Pageable pageable);

    @Modifying
    @Query("update Board board " +
            "set board.views = board.views + :views " +
            "where board.id = :boardId and board.deleted = false")
    void addViews(@Param("boardId") Long boardId, @Param("views") Long views);

    @Modifying
    @Query("update Board board " +
            "set board.adViews = board.adViews + :adVies " +
            "where board.id = :boardId and board.deleted = false")
    void addAddViews(@Param("boardId") Long boardId, @Param("adViews") Long adViews);

    @Modifying
    @Query("update Board board " +
            "set board.totalPlaytime = board.totalPlaytime + :playtime " +
            "where board.id = :boardId and board.deleted = false ")
    void addPlaytime(@Param("playtime") Long playtime, @Param("boardId") Long boardId);
}
