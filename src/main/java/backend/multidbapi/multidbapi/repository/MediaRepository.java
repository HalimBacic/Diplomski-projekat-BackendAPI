package backend.multidbapi.multidbapi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import backend.multidbapi.multidbapi.dbmodels.Media;

@Repository
public interface MediaRepository extends JpaRepository<Media, String>{
    //@Query("SELECT m FROM media m WHERE m.Tags LIKE %:keyword%")
 //   List<Media> findByTagsContaining(String tags);
}
