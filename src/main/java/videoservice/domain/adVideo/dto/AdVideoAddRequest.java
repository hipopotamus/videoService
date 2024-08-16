package videoservice.domain.adVideo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import videoservice.domain.adVideo.entity.AdVideo;

@Data
@Builder
public class AdVideoAddRequest {

    @NotNull
    MultipartFile adVideo;

    @NotBlank
    @Length(min = 2, max = 30)
    String advertiser;

    @NotNull
    Long weight;

    public AdVideo toAdVideo(String url) {
        return AdVideo.builder()
                .url(url)
                .advertiser(advertiser)
                .weight(weight)
                .build();
    }
}
