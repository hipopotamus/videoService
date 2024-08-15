package videoservice.domain.video.service;

import lombok.RequiredArgsConstructor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import videoservice.domain.video.dto.VideoAddRequest;
import videoservice.domain.video.entity.Video;
import videoservice.domain.video.repository.VideoRepository;
import videoservice.domain.video.utility.VideoUtility;
import videoservice.global.dto.IdDto;
import videoservice.global.exception.BusinessLogicException;
import videoservice.global.exception.ExceptionCode;
import videoservice.global.file.repository.FileRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

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
