package videoservice.global.generator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import videoservice.domain.account.entity.Account;
import videoservice.domain.account.entity.Gender;
import videoservice.domain.account.entity.Role;
import videoservice.domain.account.repository.AccountRepository;
import videoservice.domain.board.entity.Board;
import videoservice.domain.board.repository.BoardRepository;
import videoservice.domain.video.entity.Video;
import videoservice.domain.video.repository.VideoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Transactional
public class DataGenerateService {

    private final AccountRepository accountRepository;
    private final BoardRepository boardRepository;
    private final VideoRepository videoRepository;
    private final Random random  = new Random();

    public void generateAccount(long size) {
        //sample
        Optional<Account> optionalTestAccount = accountRepository.findByEmail("sample@sample.com");
        if (optionalTestAccount.isEmpty()) {
            Account testAccount = Account.builder()
                    .email("sample@sample.com")
                    .password("$2a$10$.s16a34pwL.M9NksIVSkIOasWPPsBB39blD1lOqinqhzoR7ri84d.")
                    .nickname("sampleNickname")
                    .gender(Gender.MALE)
                    .birthDate(LocalDate.of(1993, 12, 22))
                    .role(Role.USER)
                    .build();

            accountRepository.save(testAccount);
        }

        for (int i = 0; i < size; i++) {
            String randomWord = UUID.randomUUID().toString().substring(0, 10);

            Account account = Account.builder()
                    .email(randomWord + "@sample.com")
                    .password("$2a$10$.s16a34pwL.M9NksIVSkIOasWPPsBB39blD1lOqinqhzoR7ri84d.")
                    .nickname(randomWord)
                    .gender(Math.random() < 0.5 ? Gender.MALE : Gender.FEMALE)
                    .birthDate(getRandomBirthDate(1930, 2014))
                    .role(Role.USER)
                    .build();

            accountRepository.save(account);
        }
    }

    public void generateBoard(long size) {

        List<Long> accountIdList = accountRepository.findAll().stream()
                .map(Account::getId)
                .toList();

        for (int i = 0; i < size; i++) {
            Video video = Video.builder()
                    .url("sampleUrl")
                    .length(901L)
                    .build();
            Video savedVideo = videoRepository.save(video);

            Long randomAccountId = pickRandomValue(accountIdList);
            Board board = Board.builder()
                    .account(Account.builder().id(randomAccountId).build())
                    .video(savedVideo)
                    .title("sampleTitle")
                    .content("sampleContent")
                    .build();

            boardRepository.save(board);
        }
    }

    public void generateDailyStats() {

        List<Board> boardList = boardRepository.findAll();

        for (Board board : boardList) {
            long randomViews = random.nextLong(10000);
            boardRepository.addViews(board.getId(), randomViews);
            boardRepository.addAdViews(board.getId(), randomViews / 5);
            boardRepository.addPlaytime(randomViews * 300 + 30, board.getId());
        }
    }

    private LocalDate getRandomBirthDate(int startYear, int endYear) {

        LocalDate startDate = LocalDate.of(startYear, 1, 1);
        LocalDate endDate = LocalDate.of(endYear, 12, 31);

        long randomDay = ThreadLocalRandom.current().nextLong(startDate.toEpochDay(), endDate.toEpochDay());

        return LocalDate.ofEpochDay(randomDay);
    }

    public <T> T pickRandomValue(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("리스트가 비어있습니다.");
        }
        int index = random.nextInt(list.size());
        return list.get(index);
    }
}
