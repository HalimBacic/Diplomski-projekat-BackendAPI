package backend.multidbapi.multidbapi.models;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaRequest {
    private MultipartFile File;
    private MediaTypeEnum Type;
    private String Name;
    private String username;
}
