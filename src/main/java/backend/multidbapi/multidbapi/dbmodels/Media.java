package backend.multidbapi.multidbapi.dbmodels;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import backend.multidbapi.multidbapi.models.MediaTypeEnum;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Media")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Media {
    
    @Id
    private String Id;
    private MediaTypeEnum Type;
    private String Name;
    private long Size;
    private Date DateCreated;
    private Date DateModified;
    @Nullable
    private String Group = "";
    private Boolean Downloadable = true;
    @Column(name = "tags")
    private String Tags = "";

    @OneToMany(mappedBy = "media", cascade = CascadeType.ALL)
    Set<UserMedia> mediaOnUser = new HashSet<>();

    public Media(String id, MediaTypeEnum type, String name, long size, Date dateCreated, Date dateModified, UserMedia userMedia, String keywords) {
        this.Id = id;
        this.Type = type;
        this.Name = name;
        this.Size = size;
        this.DateCreated = dateCreated;
        this.DateModified = dateModified;
        this.Tags = name + keywords;
        mediaOnUser.add(userMedia);
    }
}
