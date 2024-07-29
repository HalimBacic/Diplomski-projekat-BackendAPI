package backend.multidbapi.multidbapi.models;

import java.io.InputStream;

import backend.multidbapi.multidbapi.dto.MediaDto;
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
    InputStream data;
}
