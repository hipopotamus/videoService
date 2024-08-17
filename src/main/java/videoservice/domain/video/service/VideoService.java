package videoservice.domain.video.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import videoservice.domain.video.dto.VideoAddRequest;
import videoservice.domain.video.entity.Video;
import videoservice.domain.video.repository.VideoRepository;
import videoservice.global.file.videofile.videoUtility.VideoUtility;
import videoservice.global.dto.IdDto;

import java.io.IOException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final VideoUtility videoUtility;

    @Transactional
    public IdDto addVideo(VideoAddRequest videoAddRequest, String path) throws IOException {

        String url = videoUtility.upload(videoAddRequest.getVideo(), path);
        long videoLength = videoUtility.getVideoLength(path, url);

        Video video = Video.builder()
                .url(url)
                .length(videoLength)
                .build();

        videoRepository.save(video);

        return new IdDto(video.getId());
    }
}
