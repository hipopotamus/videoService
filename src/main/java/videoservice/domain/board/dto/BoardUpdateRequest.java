package videoservice.domain.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import videoservice.domain.board.entity.Board;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardUpdateRequest {

    @NotBlank
    @Length(min = 1, max = 100)
    private String title;

    @NotBlank
    @Length(min = 1, max = 300)
    private String content;

    public Board toBoard() {
        return Board.builder()
                .title(title)
                .content(content)
                .build();
    }
}
