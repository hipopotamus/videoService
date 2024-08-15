package videoservice.domain.video.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import videoservice.domain.video.dto.VideoUploadRequest;
import videoservice.domain.video.dto.VideoUploadResponse;
import videoservice.domain.video.service.VideoFileService;
import videoservice.domain.video.service.VideoService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/videoFiles")
@RequiredArgsConstructor
public class VideoFileController {

    @Value("${dir}")
    private String path;

    private final VideoFileService videoFileService;

    @GetMapping("/stream/{videoName}")
    public ResponseEntity<ResourceRegion> videoStream(@RequestHeader HttpHeaders headers,
                                                      @PathVariable String videoName) throws IOException {

        Resource resource = new FileSystemResource(path + videoName);

        long chunkSize = 1024 * 1024;
        long contentLength = resource.contentLength();

        ResourceRegion region;

        try {
            HttpRange httpRange = headers.getRange().stream()
                    .findFirst().get();
            long start = httpRange.getRangeStart(contentLength);
            long end = httpRange.getRangeEnd(contentLength);
            long rangeLength = Long.min(chunkSize, end - start + 1);

            region = new ResourceRegion(resource, start, rangeLength);
        } catch (Exception e) {
            long rangeLength = Long.min(chunkSize, contentLength);
            region = new ResourceRegion(resource, 0, rangeLength);
        }

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
                .contentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .header("Accept-Ranges", "bytes")
                .eTag(path)
                .body(region);
    }

    @PostMapping
    public ResponseEntity<VideoUploadResponse> videoUpload(@Valid @ModelAttribute VideoUploadRequest videoUploadRequest) {

        String videoPath = videoFileService.upload(videoUploadRequest.getVideo(), path);

        return new ResponseEntity<>(VideoUploadResponse.of(videoPath), HttpStatus.OK);
    }
}
