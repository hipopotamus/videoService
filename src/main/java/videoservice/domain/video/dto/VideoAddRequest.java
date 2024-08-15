package videoservice.domain.video.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class VideoAddRequest {

    @NotNull
    MultipartFile video;
}
