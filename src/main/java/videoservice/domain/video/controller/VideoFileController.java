package videoservice.domain.video.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import videoservice.domain.video.dto.VideoUploadRequest;
import videoservice.domain.video.dto.VideoUploadResponse;
import videoservice.domain.video.service.VideoFileService;
import videoservice.domain.video.service.VideoService;

import java.net.MalformedURLException;
import java.util.List;

@Controller
@RequestMapping("/videoFiles")
@RequiredArgsConstructor
public class VideoFileController {

    @Value("${dir}")
    private String path;

    private final VideoFileService videoFileService;

    @GetMapping("{videoName}")
    public ResponseEntity<Resource> fileDetails(@PathVariable String videoName) throws MalformedURLException {

        Resource resource = new FileSystemResource(path + videoName);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", videoName));
        httpHeaders.setContentType(MediaType.parseMediaType("video/mp4"));

        return new ResponseEntity<>(resource, httpHeaders, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<VideoUploadResponse> videoUpload(@Valid @ModelAttribute VideoUploadRequest videoUploadRequest) {

        String videoPath = videoFileService.upload(videoUploadRequest.getVideo(), path);

        return new ResponseEntity<>(VideoUploadResponse.of(videoPath), HttpStatus.OK);
    }
}
