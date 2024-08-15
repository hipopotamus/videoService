package videoservice.global.videoUtility;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import videoservice.global.file.repository.FileRepository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Repository
public class VideoFileRepository implements FileRepository {

    @Override
    public String save(MultipartFile image, String fileName, String path) throws IOException {
        String fullPath = path + fileName;
        image.transferTo(new File(fullPath));

        return "http://localhost:8080/videoFiles/" + fileName;
    }

    @Override
    public BufferedImage download(String fileName, String path) throws IOException {
        File file = new File(path + fileName);

        return ImageIO.read(file);
    }
}
