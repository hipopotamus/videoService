package videoservice.domain.viewTime.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import videoservice.domain.account.entity.Account;
import videoservice.domain.board.entity.Board;
import videoservice.domain.viewTime.entity.ViewTime;
import videoservice.domain.viewTime.repository.ViewTimeRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ViewTimeService {

    private final ViewTimeRepository viewTimeRepository;

    @Transactional
    public boolean updateViewTime(Long accountId, Long boardId) {

        Optional<ViewTime> optionalViewTime = viewTimeRepository.findByAccountIdAndBoardId(accountId, boardId);

        if (optionalViewTime.isEmpty()) {
            ViewTime buildedViewTime = ViewTime.builder()
                    .account(Account.builder().id(accountId).build())
                    .board(Board.builder().id(boardId).build())
                    .viewTime(LocalDateTime.now())
                    .build();
            viewTimeRepository.save(buildedViewTime);

            return false;
        }

        ViewTime viewTime = optionalViewTime.get();
        LocalDateTime beforeViewTime = viewTime.getViewTime();
        viewTime.updateViewTime(LocalDateTime.now());

        return Duration.between(beforeViewTime, LocalDateTime.now()).toSeconds() < 30;
    }
}
