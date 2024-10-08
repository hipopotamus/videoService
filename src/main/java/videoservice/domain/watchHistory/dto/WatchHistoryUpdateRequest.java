package videoservice.domain.watchHistory.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import videoservice.domain.account.entity.Account;
import videoservice.domain.board.entity.Board;
import videoservice.domain.watchHistory.entity.WatchHistory;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WatchHistoryUpdateRequest {

    @NotNull
    long boardId;

    @NotNull
    long breakPoint;

    public WatchHistory toWatchHistory(Long accountId) {
        return WatchHistory.builder()
                .account(Account.builder().id(accountId).build())
                .board(Board.builder().id(boardId).build())
                .breakPoint(breakPoint)
                .build();
    }
}
