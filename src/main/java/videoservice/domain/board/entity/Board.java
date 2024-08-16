package videoservice.domain.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import videoservice.domain.account.entity.Account;
import videoservice.domain.video.entity.Video;
import videoservice.global.auditing.BaseEntity;
import videoservice.global.jpa.converter.StringLongListConverter;
import videoservice.global.jpa.converter.StringStringListConverter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    private Video video;

    private String title;

    private String content;

    private long views = 0L;

    private long adViews = 0L;

    private long totalPlayTime = 0L;

    @Convert(converter = StringStringListConverter.class)
    private List<String> adURLs = new ArrayList<>();

    @Convert(converter = StringLongListConverter.class)
    private List<Long> adTimes = new ArrayList<>();
}
