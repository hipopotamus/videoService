package videoservice.global.generator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/generate")
@RequiredArgsConstructor
public class DataGenerateController {

    private final DataGenerateService dataGenerateService;

    @PostMapping("/account/{size}")
    public ResponseEntity<String> generateAccount(@PathVariable long size) {

        dataGenerateService.generateAccount(size);

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/board/{size}")
    public ResponseEntity<String> generateBoard(@PathVariable long size) {

        dataGenerateService.generateBoard(size);

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/daily")
    public ResponseEntity<String> generateDaily() {

        dataGenerateService.generateDailyStats();

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
