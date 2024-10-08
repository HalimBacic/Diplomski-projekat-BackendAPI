package backend.multidbapi.multidbapi.models.dto;

import java.util.Date;
import backend.multidbapi.multidbapi.models.MediaTypeEnum;
import io.micrometer.common.lang.Nullable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MediaDto {
    private String Id;
    private MediaTypeEnum Type;
    private MediaTypeEnum CheckType;
    private String Name;
    private long Size;
    private Date DateCreated;
    private Date DateModified;
    @Nullable
    private String Group = "";
    private Boolean Downloadable = true;
    private String url = "";
    private String thumbUrl = "";
}
