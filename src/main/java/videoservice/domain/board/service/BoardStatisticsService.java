package videoservice.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import videoservice.domain.board.dto.BoardStatisticsListResponse;
import videoservice.domain.board.repository.BoardRepository;
import videoservice.global.dto.PageDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardStatisticsService {

    private final BoardRepository boardRepository;

    @Transactional
    public void addPlaytime(Long boardId, Long playtime) {
        boardRepository.addPlaytime(playtime, boardId);
    }

    @Transactional
    public void upBoardViews(Long boardId) {
        boardRepository.addViews(boardId, 1L);
    }

    @Transactional
    public void upAddViews(Long boardId) {
        boardRepository.addAdViews(boardId, 1L);
    }

    public PageDto<BoardStatisticsListResponse> findBoardStatisticsList(Pageable pageable) {

        Page<BoardStatisticsListResponse> pageBoardResponse =
                boardRepository.boardPageWithAccount(pageable)
                        .map(BoardStatisticsListResponse::of);

        return new PageDto<>(pageBoardResponse);
    }

    public List<BoardStatisticsListResponse> findBoardStatisticsCursor(Long lastBoardId, int limit) {
        return boardRepository.boardPageWithAccountByCursor(lastBoardId, limit);
    }
}
