package videoservice.domain.board.repository;

import videoservice.domain.board.dto.BoardStatisticsListResponse;

import java.util.List;

public interface BoardRepositoryCustom {

    List<BoardStatisticsListResponse> boardPageWithAccountByCursor(Long lastBoardId, int limit);
}
