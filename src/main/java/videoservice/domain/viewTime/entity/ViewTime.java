package videoservice.domain.viewTime.entity;

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
@NoArgsConstructor
@AllArgsConstructor
public class ViewTime extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "viewTime_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    private LocalDateTime viewTime;

    public void updateViewTime(LocalDateTime viewTime) {
        this.viewTime = viewTime;
    }
}
