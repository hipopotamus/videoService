package videoservice.domain.video.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import videoservice.domain.video.dto.VideoAddRequest;
import videoservice.domain.video.entity.Video;
import videoservice.domain.video.service.VideoService;
import videoservice.global.dto.IdDto;

import java.io.IOException;

@RestController
@RequestMapping("/videos")
@RequiredArgsConstructor
public class VideoController {

    @Value("${dir}")
    private String path;

    private final VideoService videoService;

    @PostMapping
    public ResponseEntity<IdDto> videoAdd(@Valid @ModelAttribute VideoAddRequest videoAddRequest) throws IOException {

        IdDto idDto = videoService.addVideo(videoAddRequest, path);

        return new ResponseEntity<>(idDto, HttpStatus.CREATED);
    }
}
