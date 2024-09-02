package videoservice.domain.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import videoservice.domain.board.dto.BoardStatisticsListResponse;
import videoservice.domain.board.service.BoardStatisticsService;
import videoservice.global.dto.PageDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/statistics")
public class BoardStatisticsController {

    private final BoardStatisticsService boardStatisticsService;

    @PostMapping("/views/{boardId}")
    public ResponseEntity<String> viewsUp(@PathVariable Long boardId) {

        boardStatisticsService.upBoardViews(boardId);

        return new ResponseEntity<>("Views successfully up", HttpStatus.OK);
    }

    @PostMapping("/totalPlaytime/{boardId}")
    public ResponseEntity<String> totalPlaytimeAdd(@PathVariable Long boardId, @RequestParam Long playtime) {

        boardStatisticsService.addPlaytime(boardId, playtime);

        return new ResponseEntity<>("Playtime successfully increased", HttpStatus.OK);
    }

    @PostMapping("/adViews/{boardId}")
    public ResponseEntity<String> adViewsUp(@PathVariable Long boardId) {

        boardStatisticsService.upAddViews(boardId);

        return new ResponseEntity<>("AddViews successfully up", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageDto<BoardStatisticsListResponse>> boardStatisticsList
            (@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        PageDto<BoardStatisticsListResponse> boardStatisticsResponses = boardStatisticsService.findBoardStatisticsList(pageable);

        return new ResponseEntity<>(boardStatisticsResponses, HttpStatus.OK);
    }

    @GetMapping("/cursor")
    public ResponseEntity<List<BoardStatisticsListResponse>> boardStatisticsCursor(@RequestParam Long lastBoardId, @RequestParam int limit) {

        List<BoardStatisticsListResponse> boardStatisticsCursor = boardStatisticsService.findBoardStatisticsCursor(lastBoardId, limit);

        return new ResponseEntity<>(boardStatisticsCursor, HttpStatus.OK);
    }
}
