package videoservice.domain.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import videoservice.domain.board.dto.BoardAddRequest;
import videoservice.domain.board.dto.BoardDetailsResponse;
import videoservice.domain.board.service.BoardService;
import videoservice.global.argumentresolver.LoginId;
import videoservice.global.dto.IdDto;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<IdDto> boardAdd(@LoginId Long loginId, @Valid @RequestBody BoardAddRequest boardAddRequest) {

        IdDto idDto = boardService.addBoard(loginId, boardAddRequest);

        return new ResponseEntity<>(idDto, HttpStatus.CREATED);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDetailsResponse> boardDetails(@PathVariable Long boardId) {

        BoardDetailsResponse boardDetailsResponse = boardService.findBoard(boardId);

        return new ResponseEntity<>(boardDetailsResponse, HttpStatus.OK);
    }

    @PostMapping("/statistic/views/{boardId}")
    public ResponseEntity<String> upViews(@PathVariable Long boardId) {

        boardService.upBoardViews(boardId);

        return new ResponseEntity<>("Views successfully up", HttpStatus.OK);
    }

    @PostMapping("/statistic/totalPlaytime/{boardId}")
    public ResponseEntity<String> totalPlaytimeAdd(@PathVariable Long boardId, @RequestParam Long playtime) {

        boardService.addPlaytime(boardId, playtime);

        return new ResponseEntity<>("Playtime successfully increased", HttpStatus.OK);
    }

    @PostMapping("/statistic/adViews/{boardId}")
    public ResponseEntity<String> adViewsUp(@PathVariable Long boardId) {

        boardService.upAddViews(boardId);

        return new ResponseEntity<>("AddViews successfully up", HttpStatus.OK);
    }
}
