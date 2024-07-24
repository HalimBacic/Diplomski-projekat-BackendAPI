package backend.multidbapi.multidbapi.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import backend.multidbapi.multidbapi.dto.UserDto;

@Repository
public interface UserRepository extends JpaRepository<UserDto, Long>{
    UserDto findByUsernameAndPassword(String username, String password);
    Optional<UserDetails> findByUsername(String username);
}
