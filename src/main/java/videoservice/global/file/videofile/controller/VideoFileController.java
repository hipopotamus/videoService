package videoservice.global.file.videofile.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/videoFiles")
@RequiredArgsConstructor
public class VideoFileController {

    @Value("${dir}")
    private String path;

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

    @GetMapping("/progressive/{videoName}")
    public ResponseEntity<Resource> videoProgressive(@PathVariable String videoName) throws IOException {

        FileSystemResource resource = new FileSystemResource(path + videoName);
        HttpHeaders headers = new HttpHeaders();
        String mimeType = Files.probeContentType(Paths.get(path + videoName));
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        headers.set(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", videoName));
        headers.setContentType(MediaType.parseMediaType(mimeType));

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
