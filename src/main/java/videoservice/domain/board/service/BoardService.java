package videoservice.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import videoservice.domain.adVideo.service.AdPickStrategy;
import videoservice.domain.board.dto.BoardAddRequest;
import videoservice.domain.board.dto.BoardDetailsResponse;
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
    public IdDto addBoard(Long accountId, BoardAddRequest boardAddRequest) {

        Video video = videoRepository.findById(boardAddRequest.getVideoId())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_VIDEO));
        Long videoLength = video.getLength();

        List<String> adUrl = adPickStrategy.pickAdList(videoLength / 300);
        List<Long> adTimes = getAdTimes(adUrl);

        Board board = boardAddRequest.toBoard(accountId, adUrl, adTimes);
        Board saveBoard = boardRepository.save(board);

        return new IdDto(saveBoard.getId());
    }

    @Transactional
    public BoardDetailsResponse findBoard(Long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_BOARD));

        return BoardDetailsResponse.of(board);
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
}
