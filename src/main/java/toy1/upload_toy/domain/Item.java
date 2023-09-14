package toy1.upload_toy.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Spring에서 제공하는 MultipartFile interface로 바이너리를 받아서 게시물 출력
 * 간단하게 메모리에 저장하는 형태로 할가러 setter 열었음
 */
@Getter
@Setter
public class Item {
    private Long itemId;
    private String title;
    private String writer;
    private MultipartFile attachFile;
    private List<MultipartFile> files;
}
