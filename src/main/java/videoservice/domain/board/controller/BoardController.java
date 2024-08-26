package videoservice.domain.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import videoservice.domain.board.dto.BoardAddRequest;
import videoservice.domain.board.dto.BoardDetailsResponse;
import videoservice.domain.board.dto.BoardStatisticsListResponse;
import videoservice.domain.board.dto.BoardUpdateRequest;
import videoservice.domain.board.service.BoardService;
import videoservice.global.argumentresolver.LoginId;
import videoservice.global.dto.IdDto;
import videoservice.global.dto.PageDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<IdDto> boardAdd(@LoginId Long loginId, @Valid @RequestBody BoardAddRequest boardAddRequest) {

        IdDto idDto = boardService.addBoard(loginId, boardAddRequest);

        return new ResponseEntity<>(idDto, HttpStatus.CREATED);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDetailsResponse> boardDetails(@LoginId Long loginId, @PathVariable Long boardId) {

        BoardDetailsResponse boardDetailsResponse = boardService.findBoard(loginId, boardId);

        return new ResponseEntity<>(boardDetailsResponse, HttpStatus.OK);
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<IdDto> boardUpdate(@LoginId Long loginId, @PathVariable Long boardId,
                                             @Valid @RequestBody BoardUpdateRequest boardUpdateRequest) {

        IdDto idDto = boardService.updateBoard(loginId, boardId, boardUpdateRequest);

        return new ResponseEntity<>(idDto, HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> deleteBoard(@LoginId Long loginId, @PathVariable Long boardId) {

        boardService.deleteBoard(loginId, boardId);

        return new ResponseEntity<>("Board successfully deleted", HttpStatus.OK);
    }

    @GetMapping("/statistics")
    public ResponseEntity<PageDto<BoardStatisticsListResponse>> boardStatisticsList
            (@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        PageDto<BoardStatisticsListResponse> boardStatisticsResponses = boardService.findBoardStatisticsList(pageable);

        return new ResponseEntity<>(boardStatisticsResponses, HttpStatus.OK);
    }

    @PostMapping("/statistics/views/{boardId}")
    public ResponseEntity<String> upViews(@PathVariable Long boardId) {

        boardService.upBoardViews(boardId);

        return new ResponseEntity<>("Views successfully up", HttpStatus.OK);
    }

    @PostMapping("/statistics/totalPlaytime/{boardId}")
    public ResponseEntity<String> totalPlaytimeAdd(@PathVariable Long boardId, @RequestParam Long playtime) {

        boardService.addPlaytime(boardId, playtime);

        return new ResponseEntity<>("Playtime successfully increased", HttpStatus.OK);
    }

    @PostMapping("/statistics/adViews/{boardId}")
    public ResponseEntity<String> adViewsUp(@PathVariable Long boardId) {

        boardService.upAddViews(boardId);

        return new ResponseEntity<>("AddViews successfully up", HttpStatus.OK);
    }
}
