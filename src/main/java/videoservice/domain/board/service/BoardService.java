package videoservice.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import videoservice.domain.adVideo.service.AdPickStrategy;
import videoservice.domain.board.dto.BoardAddRequest;
import videoservice.domain.board.dto.BoardDetailsResponse;
import videoservice.domain.board.dto.BoardUpdateRequest;
import videoservice.domain.board.entity.Board;
import videoservice.domain.board.repository.BoardRepository;
import videoservice.domain.video.entity.Video;
import videoservice.domain.video.repository.VideoRepository;
import videoservice.global.dto.IdDto;
import videoservice.global.exception.BusinessLogicException;
import videoservice.global.exception.ExceptionCode;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final VideoRepository videoRepository;
    private final AdPickStrategy adPickStrategy;

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

    public BoardDetailsResponse findBoard(Long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_BOARD));

        return BoardDetailsResponse.of(board);
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
            boardRepository.upViews(boardId);
    }

    @Transactional
    public void upAddViews(Long boardId) {
        boardRepository.upAddViews(boardId);
    }

    private static List<Long> getAdTimes(List<String> adUrl) {

        List<Long> adTimes = new ArrayList<>();
        for (long i = 1; i <= adUrl.size(); i++) {
            adTimes.add(300 * i);
        }

        return adTimes;
    }

    private static void verifyOwner(Long accountId, Board board) {
        if (!board.getAccount().getId().equals(accountId)) {
            throw new BusinessLogicException(ExceptionCode.FORBIDDEN);
        }
    }
}
