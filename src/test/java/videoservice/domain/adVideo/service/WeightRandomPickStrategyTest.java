package videoservice.domain.adVideo.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WeightRandomPickStrategyTest {

    private static final Logger log = LoggerFactory.getLogger(WeightRandomPickStrategyTest.class);
    @Autowired
    private WeightRandomPickStrategy weightRandomPickStrategy;

    @Test
    @DisplayName("가중치 랜덤 광고 뽑기_성공")
    public void randomWeightPick_Success() {

        //when
        List<String> adUrlList = weightRandomPickStrategy.pickAdList(3);

        //then
        assertThat(adUrlList).hasSize(3);
        assertThat(adUrlList).allMatch(url -> url.startsWith("testURL"));
    }

    @Test
    @DisplayName("가중치 랜덤 광고 뽑기_0개")
    public void randomWeightPick_SizeZero() {

        //when
        List<String> adUrlList = weightRandomPickStrategy.pickAdList(0);

        //then
        assertThat(adUrlList).isEmpty();
    }

    @Test
    @DisplayName("가중치 랜덤 광고 뽑기_광고 영상보다 많을 때")
    public void randomWeightPick_SizeGreaterThanTotal() {

        //when
        List<String> adUrlList = weightRandomPickStrategy.pickAdList(10);

        //then
        assertThat(adUrlList).hasSize(10);
        assertThat(adUrlList).allMatch(url -> url.startsWith("testURL"));
    }


}