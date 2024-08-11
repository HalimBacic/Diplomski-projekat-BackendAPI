package backend.multidbapi.multidbapi.repository;

import backend.multidbapi.multidbapi.dbmodels.UserMedia;
import backend.multidbapi.multidbapi.dbmodels.UserMediaKey;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserMediaRepository extends JpaRepository<UserMedia, UserMediaKey> {
    void deleteById_UserIdAndId_MediaId(String userId, String mediaId);
}