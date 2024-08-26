package videoservice.domain.watchHistory.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import videoservice.domain.watchHistory.dto.WatchHistoryUpdateRequest;
import videoservice.domain.watchHistory.service.WatchHistoryService;
import videoservice.global.argumentresolver.LoginId;
import videoservice.global.dto.IdDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/watchHistory")
public class WatchHistoryController {

    private final WatchHistoryService watchHistoryService;

    @PutMapping
    public ResponseEntity<IdDto> watchHistoryUpdate(@LoginId Long loginId,
                                                    @Valid @RequestBody WatchHistoryUpdateRequest watchHistoryUpdateRequest) {

        IdDto idDto = watchHistoryService.updateWatchHistory(loginId, watchHistoryUpdateRequest);

        return new ResponseEntity<>(idDto, HttpStatus.OK);
    }
}
