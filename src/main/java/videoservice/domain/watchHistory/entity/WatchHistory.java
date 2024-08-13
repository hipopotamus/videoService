package videoservice.domain.watchHistory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import videoservice.domain.account.entity.Account;
import videoservice.domain.board.entity.Board;
import videoservice.global.auditing.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WatchHistory extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "watchHistory_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private Long watchDuration;

    private Long breakPoint;

    private LocalDateTime viewTime;
}
