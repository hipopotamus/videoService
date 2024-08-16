package videoservice.domain.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import videoservice.domain.account.entity.Account;
import videoservice.domain.board.entity.Board;
import videoservice.domain.video.entity.Video;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardAddRequest {

    @NotNull
    private Long videoId;

    @NotBlank
    @Length(min = 1, max = 100)
    private String title;

    @NotBlank
    @Length(min = 1, max = 300)
    private String content;

    public Board toBoard(Long accountId, List<String> adUrls, List<Long> adTimes) {
        return Board.builder()
                .account(Account.builder().id(accountId).build())
                .video(Video.builder().id(videoId).build())
                .title(title)
                .content(content)
                .adURLs(adUrls)
                .adTimes(adTimes)
                .build();
    }
}
