package videoservice.global.file.videofile.videoUtility;

import lombok.RequiredArgsConstructor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import videoservice.global.exception.BusinessLogicException;
import videoservice.global.exception.ExceptionCode;
import videoservice.global.file.repository.FileRepository;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class VideoUtility {

    private final FileRepository fileRepository;
    private final FFprobe fFprobe;

    public long getVideoLength(String path, String url) throws IOException {
        int pos = url.lastIndexOf("/");
        String filename = url.substring(pos);
        FFmpegProbeResult probe = fFprobe.probe(path + filename);

        long videoLength = (long) probe.format.duration;
        return videoLength;
    }

    public String upload(MultipartFile video, String path) {

        verifyVideo(video);

        String filename = createFilename(video);

        try {
            return fileRepository.save(video, filename, path);
        } catch (IOException e) {
            throw new BusinessLogicException(ExceptionCode.UPLOAD_FAILED);
        }
    }

    private String createFilename(MultipartFile image) {

        String originalFilename = image.getOriginalFilename();
        int dotIndex = originalFilename.lastIndexOf(".");

        return UUID.randomUUID() + originalFilename.substring(dotIndex);
    }

    private void verifyVideo(MultipartFile video) {

        if (video == null || video.isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.EMPTY_FILE);
        }

        String videoName = video.getOriginalFilename();

        if (videoName == null) {
            throw new BusinessLogicException(ExceptionCode.ILLEGAL_FILENAME);
        }

        boolean matches = videoName.matches("^\\S.*\\.(mp4|MP4|avi|AVI|mkv|MKV)$");
        if (!matches) {
            throw new BusinessLogicException(ExceptionCode.ILLEGAL_FILENAME);
        }
    }

}
