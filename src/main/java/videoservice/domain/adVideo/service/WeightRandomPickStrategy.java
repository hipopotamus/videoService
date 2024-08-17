package videoservice.domain.adVideo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import videoservice.domain.adVideo.entity.AdVideo;
import videoservice.domain.adVideo.entity.TotalWeight;
import videoservice.domain.adVideo.repository.AdVideoRepository;
import videoservice.domain.adVideo.repository.TotalWeightRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WeightRandomPickStrategy implements AdPickStrategy {

    private final TotalWeightRepository totalWeightRepository;
    private final AdVideoRepository adVideoRepository;

    public void addWeight(Long weight) {

        TotalWeight totalWeight = totalWeightRepository.findAll().getFirst();
        totalWeightRepository.addWeight(weight, totalWeight.getId());
    }

    @Override
    public List<String> pickAdList(long size) {

        List<String> adUrls = new ArrayList<>();

        Random random = new Random();
        Long totalWeight = totalWeightRepository.findAll().getFirst().getTotalWeight();
        List<AdVideo> adVideoList = adVideoRepository.findAll();

        for (int i = 0; i < size; i++) {
            long randomIndex = random.nextLong(totalWeight);
            int currentSum = 0;
            for (AdVideo adVideo : adVideoList) {
                currentSum += adVideo.getWeight();
                if (randomIndex < currentSum) {
                    adUrls.add(adVideo.getUrl());
                    break;
                }
            }
        }
        return adUrls;
    }
}
