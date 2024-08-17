package videoservice.domain.watchHistory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import videoservice.domain.watchHistory.dto.WatchHistoryUpdateRequest;
import videoservice.domain.watchHistory.entity.WatchHistory;
import videoservice.domain.watchHistory.repository.WatchHistoryRepository;
import videoservice.global.dto.IdDto;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WatchHistoryService {

    private final WatchHistoryRepository watchHistoryRepository;

    @Transactional
    public IdDto updateWatchHistory(Long accountId, WatchHistoryUpdateRequest watchHistoryUpdateRequest) {

        Optional<WatchHistory> optionalWatchHistory =
                watchHistoryRepository.findByAccountAndBoard(accountId, watchHistoryUpdateRequest.getBoardId());
        WatchHistory updateWatchHistory = watchHistoryUpdateRequest.toWatchHistory(accountId);

        if (optionalWatchHistory.isEmpty()) {
            WatchHistory savedWatchHistory = watchHistoryRepository.save(updateWatchHistory);

            return new IdDto(savedWatchHistory.getId());
        }

        WatchHistory watchHistory = optionalWatchHistory.get();
        watchHistory.modify(updateWatchHistory);

        return new IdDto(watchHistory.getId());
    }
}
