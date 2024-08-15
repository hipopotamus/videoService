package videoservice.domain.adVideo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TotalWeight {

    @Id
    @GeneratedValue
    @Column(name = "totalWeight_id")
    private Long id;

    Long totalWeight;
}
