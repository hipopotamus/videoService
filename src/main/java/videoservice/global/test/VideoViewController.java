package videoservice.global.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/viewTest")
@RequiredArgsConstructor
public class VideoViewController {

    @GetMapping("/{videoName}")
    public String viewVideo(@PathVariable String videoName, Model model) {
        model.addAttribute("videoName", videoName);
        return "video";
    }
}
