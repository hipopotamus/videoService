package videoservice.domain.video.dto;

import lombok.Builder;
import lombok.Data;
import videoservice.domain.video.entity.Video;

@Data
@Builder
public class VideoUploadResponse {

    String url;

    public static VideoUploadResponse of(String url) {
        return VideoUploadResponse.builder()
                .url(url)
                .build();
    }
}
