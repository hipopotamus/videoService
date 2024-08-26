package videoservice.domain.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import videoservice.domain.board.dto.BoardAddRequest;
import videoservice.domain.board.dto.BoardDetailsResponse;
import videoservice.domain.board.dto.BoardUpdateRequest;
import videoservice.domain.board.service.BoardService;
import videoservice.global.argumentresolver.LoginId;
import videoservice.global.dto.IdDto;

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
}
