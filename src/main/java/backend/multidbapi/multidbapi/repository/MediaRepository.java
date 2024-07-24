package backend.multidbapi.multidbapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.multidbapi.multidbapi.dto.MediaDto;

@Repository
public interface MediaRepository extends JpaRepository<MediaDto, Long>{
    
}
