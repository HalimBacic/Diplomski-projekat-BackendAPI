package backend.multidbapi.multidbapi.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaDownloadRequest {
    private String mediaId;
    private String name;
}
