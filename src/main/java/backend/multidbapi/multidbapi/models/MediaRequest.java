package backend.multidbapi.multidbapi.models;

import org.springframework.web.multipart.MultipartFile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaRequest {
    private String keywords = "";
    private MultipartFile File;
    private MediaTypeEnum Type;
    private String Name;
    private String username;
}
