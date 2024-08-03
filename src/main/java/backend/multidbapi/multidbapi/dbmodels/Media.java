package backend.multidbapi.multidbapi.dbmodels;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import backend.multidbapi.multidbapi.models.MediaTypeEnum;
import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
    
    public Media(MediaTypeEnum type, String name, long size, Date dateCreated, Date dateModified, String group, User userAccess) {
        this.Type = type;
        this.Name = name;
        this.Size = size;
        this.DateCreated = dateCreated;
        this.DateModified = dateModified;
        this.Group = group;
        this.UsersAccess.add(userAccess);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private MediaTypeEnum Type;
    private String Name;
    private long Size;
    private Date DateCreated;
    private Date DateModified;
    @Nullable
    private String Group = "";
    private Boolean Downloadable = true;
    @JsonManagedReference
    @ManyToMany(mappedBy = "UserMedia")
    Set<User> UsersAccess = new HashSet<>();
}
