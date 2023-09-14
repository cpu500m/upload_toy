package toy1.upload_toy.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import toy1.upload_toy.domain.Post;
import toy1.upload_toy.repository.ItemRepository;
import toy1.upload_toy.repository.PostRepository;

import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final ItemRepository itemRepository;
    private final PostRepository postRepository;

    @GetMapping("/")
    public String goHome(Model model) {
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "home";
    }

    /**
     * 테스트용
     */
    @PostConstruct
    public void init() {
        // post랑 item 1개씩 생성 후 넣어줄거임
    }
}
