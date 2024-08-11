package backend.multidbapi.multidbapi.dbmodels;

import backend.multidbapi.multidbapi.models.GrantEnum;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class UserMedia {
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    UserMediaKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userId")
    User user;

    @ManyToOne
    @MapsId("mediaId")
    @JoinColumn(name = "mediaId")
    Media media;

    GrantEnum grants;

    public UserMedia(User user, String uuid, GrantEnum grant)
    {
        this.user = user;
        this.id = new UserMediaKey(user.getUsername(), uuid);
        this.grants = grant;
    }

    public UserMedia(User user, Media media, GrantEnum grant)
    {
        this.user = user;
        this.media = media;
        this.id = new UserMediaKey(user.getUsername(), media.getId());
        this.grants = grant;
    }
}
