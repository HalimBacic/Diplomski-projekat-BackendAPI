package backend.multidbapi.multidbapi.services;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import backend.multidbapi.multidbapi.dbmodels.Media;
import backend.multidbapi.multidbapi.dbmodels.User;
import backend.multidbapi.multidbapi.dbmodels.UserMedia;
import backend.multidbapi.multidbapi.dbmodels.UserMediaKey;
import backend.multidbapi.multidbapi.interfaces.MediaInterface;
import backend.multidbapi.multidbapi.models.GrantEnum;
import backend.multidbapi.multidbapi.models.MediaRequest;
import backend.multidbapi.multidbapi.models.MediaTypeEnum;
import backend.multidbapi.multidbapi.models.UpdateMediaRequest;
import backend.multidbapi.multidbapi.models.dto.MediaDto;
import backend.multidbapi.multidbapi.repository.MediaRepository;
import backend.multidbapi.multidbapi.repository.UserMediaRepository;
import backend.multidbapi.multidbapi.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import jakarta.persistence.EntityNotFoundException;

@Service
public class MediaService implements MediaInterface {

    @Autowired
    public MediaRepository mediaRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public UserMediaRepository userMediaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Override
    public Media UploadFile(MediaRequest mediaRequest) throws Exception {
        User user = userRepository.findById(mediaRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        UUID uuidId = UUID.randomUUID();
        UserMedia userMedia = new UserMedia(user, uuidId.toString(), GrantEnum.WRITE);
        Media mediaDTO = new Media(uuidId.toString(), mediaRequest.getType(),
                mediaRequest.getName(),
                mediaRequest.getFile().getSize(),
                new Date(),
                new Date(),
                userMedia,
                mediaRequest.getKeywords());
        userMedia.setUser(user);
        userMedia.setMedia(mediaDTO);
        mediaRepository.save(mediaDTO);
        return mediaDTO;
    }

    @Override
    public Media PreviewFile(String mediaId) throws Exception {
        Media metadata = mediaRepository.getReferenceById(mediaId);
        return metadata;
    }

    @Override
    public Media UpdateFile(String id, UpdateMediaRequest mediaRequest) throws Exception {
        Media media = mediaRepository.getReferenceById(id);
        media.setGroup(mediaRequest.getGroup());
        media.setDownloadable(mediaRequest.getDownloadable());
        media.setName(mediaRequest.getName());
        media.setDateModified(new Date());
        mediaRepository.save(media);
        return media;
    }

    @Override
    public Boolean GiveFileAccess(List<String> usersId, String ownerId, String mediaId, GrantEnum grant)
            throws Exception {
        User owner = userRepository.findById(ownerId).get();
        Media media = mediaRepository.findById(mediaId).get();
        for (String userId : usersId) {
            User user = userRepository.findById(userId).get();
            Optional<UserMedia> userMediaDb = userMediaRepository
                    .findById(new UserMediaKey(media.getId(), user.getUsername()));
            userMediaDb.ifPresentOrElse(um -> {
                if (grant == GrantEnum.NONE) {
                    userMediaRepository.delete(um);
                } else {
                    um.setGrants(grant);
                    userMediaRepository.save(um);
                }
            }, () -> {
                UserMedia userMedia = new UserMedia(user, media, grant);
                media.getMediaOnUser().add(userMedia);
                mediaRepository.save(media);
            });
        }

        return true;
    }

    @Override
    public String DownloadFile(String mediaId, String userId) throws Exception {
        Media media = mediaRepository.getReferenceById(mediaId);
        UserMediaKey userMediaKey = new UserMediaKey(mediaId, userId);
        UserMedia userMedia = userMediaRepository.findById(userMediaKey).orElseThrow(() -> new EntityNotFoundException("User has no access to media"));;
        if(userMedia.getGrants() != GrantEnum.NONE)
            return media.getName();
        else
            throw new Exception("User can't download file.");
    }

    public void saveFile(InputStream inputStream, String filePath) throws IOException {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filePath))) {

            byte[] buffer = new byte[1024 * 1024 * 1024];
            int bytesRead;
            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    @Override
    public String DeleteFile(String id) {
        Media media = mediaRepository.getReferenceById(id);
        String mediaName = media.getName();
        try {
            mediaRepository.delete(media);
            return mediaName;
        } catch (Exception ex) {
            return null;
        }
    }

    // @Override
    // public List<Media> SearchFiles(String term) throws Exception {
    // return mediaRepository.findByTagsContaining(term);
    // }

    @Override
    public Boolean RestrictFileAccess(List<String> usersId, String ownerId, String mediaId) throws Exception {
        for (String userId : usersId) {
            userMediaRepository.deleteById_UserIdAndId_MediaId(userId, mediaId);
        }
        return true;
    }

    @Override
    public List<MediaDto> GetAllFiles(String username) throws Exception {
        UserMedia userMedia = new UserMedia();
        userMedia.setUser(new User(username));
        List<UserMedia> usermedia = userMediaRepository.findAll(Example.of(userMedia));
        List<MediaDto> mediaList = new ArrayList<>();
        for (UserMedia media : usermedia) {
            MediaDto mediaDto = modelMapper.map(media.getMedia(), MediaDto.class);
            mediaDto.setCheckType(MediaTypeEnum.CheckMimeType(mediaDto.getType()));
            mediaList.add(mediaDto);
        }

        return mediaList;
    }

    public String GetUserNameByToken(String authorizationHeader)
    {
            String token = authorizationHeader.substring(7);
            String username = Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return username;
    }
}
