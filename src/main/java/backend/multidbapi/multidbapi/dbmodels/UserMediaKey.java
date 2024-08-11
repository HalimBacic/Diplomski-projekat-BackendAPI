package backend.multidbapi.multidbapi.dbmodels;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class UserMediaKey implements Serializable{
    @Column(name = "mediaId")
    String mediaId;

    @Column(name = "userId")
    String userId;
}
