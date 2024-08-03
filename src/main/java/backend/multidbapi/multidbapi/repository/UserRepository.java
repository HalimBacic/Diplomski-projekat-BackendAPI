package backend.multidbapi.multidbapi.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import backend.multidbapi.multidbapi.dbmodels.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
    User findByUsernameAndPassword(String id, String password);
    Optional<UserDetails> findByUsername(String id);
}
