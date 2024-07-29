package backend.multidbapi.multidbapi.models;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

@Getter
public class MediaRequest {
    public Long MediaId;
    public MultipartFile File;
    private MediaTypeEnum Type;
    private String Name;
    private long Size;
    private Date DateCreated;
    private Date DateModified;
    private String Group = "";
    private Long userId;
    private String username;
}
