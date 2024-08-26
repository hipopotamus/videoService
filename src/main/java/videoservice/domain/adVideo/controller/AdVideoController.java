package videoservice.domain.adVideo.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import videoservice.domain.adVideo.dto.AdVideoAddRequest;
import videoservice.domain.adVideo.service.AdVideoService;
import videoservice.global.dto.IdDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/adVideos")
public class AdVideoController {

    @Value("${dir}")
    private String path;

    private final AdVideoService adVideoService;

    @PostMapping
    public ResponseEntity<IdDto> adVideoAdd(@Valid @ModelAttribute AdVideoAddRequest adVideoAddRequest) {

        IdDto idDto = adVideoService.addAdVideo(adVideoAddRequest, path);

        return new ResponseEntity<>(idDto, HttpStatus.CREATED);
    }

}
