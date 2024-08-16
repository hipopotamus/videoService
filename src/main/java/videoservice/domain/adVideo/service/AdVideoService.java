package videoservice.domain.adVideo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import videoservice.domain.adVideo.dto.AdVideoAddRequest;
import videoservice.domain.adVideo.entity.AdVideo;
import videoservice.domain.adVideo.repository.AdVideoRepository;
import videoservice.global.dto.IdDto;
import videoservice.global.videoUtility.VideoUtility;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdVideoService {

    private final AdVideoRepository adVideoRepository;
    private final WeightRandomPickService WeightRandomPickService;
    private final VideoUtility videoUtility;

    @Transactional
    public IdDto addAdVideo(AdVideoAddRequest adVideoAddRequest, String path) {

        String url = videoUtility.upload(adVideoAddRequest.getAdVideo(), path);
        AdVideo adVideo = adVideoAddRequest.toAdVideo(url);

        AdVideo savedAdVideo = adVideoRepository.save(adVideo);

        WeightRandomPickService.addWeight(adVideoAddRequest.getWeight());

        return new IdDto(savedAdVideo.getId());
    }

}
