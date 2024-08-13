package videoservice.domain.adVideo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import videoservice.global.auditing.BaseEntity;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdVideo extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "adVideo_id")
    private Long id;

    private String url;

    private String advertiser;
}
