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
    public ResponseEntity<IdDto> boardAdd(@LoginId Long accountId, @Valid @RequestBody BoardAddRequest boardAddRequest) {

        IdDto idDto = boardService.addBoard(boardAddRequest, accountId);

        return new ResponseEntity<>(idDto, HttpStatus.CREATED);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDetailsResponse> boardDetails(@LoginId Long accountId, @PathVariable Long boardId) {

        BoardDetailsResponse boardDetailsResponse = boardService.findBoard(accountId, boardId);

        return new ResponseEntity<>(boardDetailsResponse, HttpStatus.OK);
    }
}
