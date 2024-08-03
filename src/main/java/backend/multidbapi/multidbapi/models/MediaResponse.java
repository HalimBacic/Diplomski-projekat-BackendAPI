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
@AllArgsConstructor
@NoArgsConstructor
public class MediaResponse {
    MediaDto metadata;
    ByteArrayResource resource;

    public MediaResponse(MediaDto mediadto, InputStream media) throws IOException
    {
        byte[] mediabytes = media.readAllBytes();
        resource = new ByteArrayResource(mediabytes);
        metadata = mediadto;
    }
}
