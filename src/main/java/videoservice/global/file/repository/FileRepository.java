package videoservice.global.file.repository;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface FileRepository {

    String save(MultipartFile image, String fileName, String path) throws IOException;

    BufferedImage download(String fileName, String path) throws IOException;
}
