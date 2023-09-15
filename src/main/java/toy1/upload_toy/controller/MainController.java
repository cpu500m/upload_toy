package toy1.upload_toy.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;
import toy1.upload_toy.domain.Item;
import toy1.upload_toy.domain.ItemForm;
import toy1.upload_toy.domain.Post;
import toy1.upload_toy.file.UploadFile;
import toy1.upload_toy.repository.ItemRepository;
import toy1.upload_toy.repository.PostRepository;
import toy1.upload_toy.service.FileService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final ItemRepository itemRepository;
    private final PostRepository postRepository;

    private final FileService fileService;

    @GetMapping("/")
    public String goHome(Model model) {
        log.debug("home");
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "home";
    }

    /**
     * 게시물 등록
     */
    @GetMapping("/addPost")
    public String post(@ModelAttribute ItemForm itemForm) {
        log.debug("post");
        return "addPost";
    }

    @PostMapping("/addPost")
    public String addPost(@ModelAttribute ItemForm itemForm, RedirectAttributes redirectAttributes) throws IOException {
        List<UploadFile> imageFiles = fileService.storeFiles(itemForm.getImageFiles());
        UploadFile attachFile = fileService.storeFile(itemForm.getAttachFile());

        log.debug("addPost");

        /* 저장 */

        // item
        Item item = Item.createItem(itemForm.getTitle(), itemForm.getWriter(), itemForm.getText()
                , imageFiles, attachFile);
        itemRepository.save(item);

        // post
        Post post = Post.createPost(itemForm.getTitle(), itemForm.getWriter());
        postRepository.save(post);

        redirectAttributes.addAttribute("itemId", item.getItemId());
        return "redirect:/post/{itemId}";
    }

    /**
     * 게시물 열람
     */
    @GetMapping("/post/{itemId}")
    public String readPost(@PathVariable Long itemId, Model model) {
        log.debug("readPost");
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "post";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource downloadImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + fileService.getFullPath(filename));
    }

    @GetMapping("/attach/{itemId}")
    public ResponseEntity<Resource> download(@PathVariable Long itemId) throws MalformedURLException {
        Item item = itemRepository.findById(itemId);
        String storeName = item.getAttachFile().getStoreName();
        String originName = item.getAttachFile().getOriginName();

        UrlResource resource = new UrlResource("file:" + fileService.getFullPath(storeName));

        String encodedOriginName = UriUtils.encode(originName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedOriginName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}
