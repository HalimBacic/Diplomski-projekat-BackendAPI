package backend.multidbapi.multidbapi.dbmodels;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "User")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
   @Id
   private String username;
   private String password;
   private String email;
   private List<String> authorities;

   public User(String username, String password, String email, List<String> authorities) {
      super();
      this.username = username;
      this.password = password;
      this.email = email;
      this.authorities = authorities;
   }

    @OneToMany(mappedBy = "user")
    Set<UserMedia> mediaOnUser;
}
