package backend.multidbapi.multidbapi.dto;

import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import backend.multidbapi.multidbapi.models.MediaTypeEnum;
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
public class MediaDto {
    
    public MediaDto(MediaTypeEnum type, String name, long size, Date dateCreated, Date dateModified, String group, UserDto userAccess) {
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
    private String Group = "";

    @ManyToMany(mappedBy = "UserMedia")
    Set<UserDto> UsersAccess = new HashSet<>();
}
