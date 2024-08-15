package videoservice.domain.adVideo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import videoservice.domain.adVideo.entity.TotalWeight;

public interface TotalWeightRepository extends JpaRepository<TotalWeight, Long> {

    @Modifying
    @Query("update TotalWeight totalweight " +
            "set totalweight.totalWeight = totalweight.totalWeight + :weight " +
            "where totalweight.id = :id")
    void addWeight(@Param("weight") Long weight, @Param("id") Long id);
}
