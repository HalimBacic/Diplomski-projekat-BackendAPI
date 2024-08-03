package backend.multidbapi.multidbapi.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaDownloadRequest {
    private Long mediaId;
    private String name;
    private String username;
}
