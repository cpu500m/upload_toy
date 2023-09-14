package toy1.upload_toy.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 홈에서 게시물 리스트 보여주기 위함
 */
@Getter @Setter
public class Post {
    private Long postId;
    private String title;
    private String writer;

}
