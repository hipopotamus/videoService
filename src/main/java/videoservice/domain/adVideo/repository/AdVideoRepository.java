package videoservice.domain.adVideo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import videoservice.domain.adVideo.entity.AdVideo;

public interface AdVideoRepository extends JpaRepository<AdVideo, Long> {
}
