package videoservice.global.config;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFprobe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class AppConfig {

    @Value("${ffmpeg}/bin/ffmpeg")
    public String ffmpegPath;

    @Value("${ffmpeg}/bin/ffprobe")
    public String ffprobePath;

    @Bean
    public FFmpeg fFmpeg() throws IOException {
        return new FFmpeg(ffmpegPath);
    }

    @Bean
    public FFprobe fFprobe() throws IOException {
        return new FFprobe(ffprobePath);
    }
}
