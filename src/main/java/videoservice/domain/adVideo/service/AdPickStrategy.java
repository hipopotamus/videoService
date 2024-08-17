package videoservice.domain.adVideo.service;

import java.util.List;

public interface AdPickStrategy {

    List<String> pickAdList(long size);
}
