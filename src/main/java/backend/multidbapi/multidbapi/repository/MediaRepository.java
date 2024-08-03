package backend.multidbapi.multidbapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import backend.multidbapi.multidbapi.dbmodels.Media;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long>{
    
}
