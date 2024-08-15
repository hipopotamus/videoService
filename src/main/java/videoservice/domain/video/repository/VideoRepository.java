package videoservice.domain.video.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import videoservice.domain.video.entity.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {
}
