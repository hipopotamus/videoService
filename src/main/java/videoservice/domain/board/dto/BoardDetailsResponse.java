package videoservice.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import videoservice.domain.board.entity.Board;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDetailsResponse {

    private String videoURL;

    private String title;

    private String content;

    private long views;

    private List<String> adURLs = new ArrayList<>();

    private List<Long> adTimes = new ArrayList<>();

    private boolean isAbuse;

    public static BoardDetailsResponse of(Board board, boolean isAbuse) {
        return BoardDetailsResponse.builder()
                .videoURL(board.getVideo().getUrl())
                .title(board.getTitle())
                .content(board.getContent())
                .views(board.getViews())
                .adURLs(board.getAdURLs())
                .adTimes(board.getAdTimes())
                .isAbuse(isAbuse)
                .build();
    }

}
