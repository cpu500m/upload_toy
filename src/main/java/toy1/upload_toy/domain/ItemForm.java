package toy1.upload_toy.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ItemForm {
    private Long itemId;
    private String title;
    private String writer;
    private String text;
    private List<MultipartFile> imageFiles;
    private MultipartFile attachFile;
}
