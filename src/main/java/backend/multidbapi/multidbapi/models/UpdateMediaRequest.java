package backend.multidbapi.multidbapi.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMediaRequest {
    private String name;
    private String group = "";
    private Boolean downloadable = true;
}
