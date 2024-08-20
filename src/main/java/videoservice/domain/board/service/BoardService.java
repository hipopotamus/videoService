package videoservice.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import videoservice.domain.adVideo.service.AdPickStrategy;
import videoservice.domain.board.dto.BoardAddRequest;
import videoservice.domain.board.dto.BoardDetailsResponse;
import videoservice.domain.board.dto.BoardStatisticListResponse;
import videoservice.domain.board.dto.BoardUpdateRequest;
import videoservice.domain.board.entity.Board;
import videoservice.domain.board.repository.BoardRepository;
import videoservice.domain.video.entity.Video;
import videoservice.domain.video.repository.VideoRepository;
import videoservice.domain.watchHistory.entity.WatchHistory;
import videoservice.domain.watchHistory.repository.WatchHistoryRepository;
import videoservice.global.dto.IdDto;
import videoservice.global.dto.PageDto;
import videoservice.global.exception.BusinessLogicException;
import videoservice.global.exception.ExceptionCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final VideoRepository videoRepository;
    private final AdPickStrategy adPickStrategy;
    private final WatchHistoryRepository watchHistoryRepository;

    @Transactional
    public IdDto addBoard(Long loginId, BoardAddRequest boardAddRequest) {

        Video video = videoRepository.findById(boardAddRequest.getVideoId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_VIDEO));
        Long videoLength = video.getLength();

        List<String> adUrl = adPickStrategy.pickAdList(videoLength / 300);
        List<Long> adTimes = getAdTimes(adUrl);

        Board board = boardAddRequest.toBoard(loginId, adUrl, adTimes);
        Board saveBoard = boardRepository.save(board);

        return new IdDto(saveBoard.getId());
    }

    public BoardDetailsResponse findBoard(Long loginId, Long boardId) {

        long breakPoint = getBreakPoint(loginId, boardId);

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_BOARD));

        return BoardDetailsResponse.of(board, breakPoint);
    }

    public PageDto<BoardStatisticListResponse> findBoardStatistics(Pageable pageable) {

        Page<BoardStatisticListResponse> pageBoardResponse =
                boardRepository.findStatisticList(pageable)
                        .map(BoardStatisticListResponse::of);

        return new PageDto<>(pageBoardResponse);
    }

    @Transactional
    public IdDto updateBoard(Long loginId, Long boardId, BoardUpdateRequest boardUpdateRequest) {

        Board board = boardRepository.findByIdWithAccount(boardId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_BOARD));

        verifyOwner(loginId, board);

        Board updateSource = boardUpdateRequest.toBoard();
        board.modify(updateSource);

        return new IdDto(board.getId());
    }

    @Transactional
    public void deleteBoard(Long loginId, Long boardId) {

        Board board = boardRepository.findByIdWithAccount(boardId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_BOARD));

        verifyOwner(loginId, board);

        board.softDelete();
    }


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
        boardRepository.addAddViews(boardId, 1L);
    }

    private static List<Long> getAdTimes(List<String> adUrl) {

        List<Long> adTimes = new ArrayList<>();
        for (long i = 1; i <= adUrl.size(); i++) {
            adTimes.add(300 * i);
        }

        return adTimes;
    }

    private long getBreakPoint(Long loginId, Long boardId) {

        long breakPoint = 0;

        Optional<WatchHistory> optionalWatchHistory =
                watchHistoryRepository.findByAccountAndBoard(loginId, boardId);
        if (optionalWatchHistory.isPresent()) {
            WatchHistory watchHistory = optionalWatchHistory.get();
            breakPoint = watchHistory.getBreakPoint();

        }
        return breakPoint;
    }

    private static void verifyOwner(Long accountId, Board board) {
        if (!board.getAccount().getId().equals(accountId)) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }
    }
}
