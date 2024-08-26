package videoservice.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import videoservice.domain.board.entity.Board;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardStatisticsListResponse {

    private Long accountId;

    private Long boardId;

    private long views;

    private long adViews;

    private long totalPlaytime;

    public static BoardStatisticsListResponse of(Board board) {
        return BoardStatisticsListResponse.builder()
                .accountId(board.getAccount().getId())
                .boardId(board.getId())
                .views(board.getViews())
                .adViews(board.getAdViews())
                .totalPlaytime(board.getTotalPlaytime())
                .build();
    }
}
