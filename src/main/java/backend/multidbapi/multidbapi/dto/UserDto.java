package backend.multidbapi.multidbapi.dto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "User")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id; 
   private String username;
   private String password;
   private String email;
   private List<String> authorities;

   public UserDto(String username, String password, String email, List<String> authorities) {
      super();
      this.username = username;
      this.password = password;
      this.email = email;
      this.authorities = authorities;
   }

   @ManyToMany
   @JoinTable(name = "usermedia", joinColumns = @JoinColumn(name="userId"), inverseJoinColumns = @JoinColumn(name = "mediaId"))
   Set<MediaDto> UserMedia = new HashSet<>();
}
