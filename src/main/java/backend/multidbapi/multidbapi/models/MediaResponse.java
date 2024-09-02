package backend.multidbapi.multidbapi.models;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.core.io.ByteArrayResource;
import backend.multidbapi.multidbapi.models.dto.MediaDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MediaResponse {
    MediaDto metadata;
    String thumburl;
    String url;

    public MediaResponse(MediaDto mediadto, String url, String turl) throws IOException
    {
        metadata = mediadto;
        metadata.setUrl(url);
        metadata.setThumbUrl(turl);
    }
}
