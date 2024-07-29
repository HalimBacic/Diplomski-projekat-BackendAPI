package backend.multidbapi.multidbapi.services;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.multidbapi.multidbapi.dto.MediaDto;
import backend.multidbapi.multidbapi.dto.UserDto;
import backend.multidbapi.multidbapi.interfaces.MediaInterface;
import backend.multidbapi.multidbapi.models.MediaRequest;
import backend.multidbapi.multidbapi.repository.MediaRepository;
import backend.multidbapi.multidbapi.repository.UserRepository;

@Service
public class MediaService implements MediaInterface {

    @Autowired
    public MediaRepository mediaRepository;

    @Autowired
    public UserRepository userRepository;

    @Override
    public MediaDto UploadFile(MediaRequest mediaRequest) throws Exception {
        UserDto user = userRepository.getReferenceById(mediaRequest.getUserId());
        MediaDto mediaDTO = new MediaDto(mediaRequest.getType(),
                mediaRequest.getName(),
                mediaRequest.getFile().getSize(),
                new Date(),
                new Date(),
                null,
                user);
        mediaRepository.save(mediaDTO);
        return mediaDTO;
    }

    @Override
    public MediaDto DownloadFile(MediaRequest mediaRequest) throws Exception {
        MediaDto metadata = mediaRepository.getReferenceById(mediaRequest.MediaId);
        return metadata;
    }

    @Override
    public MediaDto UpdateFile(MediaRequest mediaRequest) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'UpdateFile'");
    }

    @Override
    public MediaDto GiveFileAccess(List<UserDto> user, int mediaId) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GiveFile'");
    }

}
