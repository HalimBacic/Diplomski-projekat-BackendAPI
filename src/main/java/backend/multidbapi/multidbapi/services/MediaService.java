package backend.multidbapi.multidbapi.services;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import backend.multidbapi.multidbapi.dbmodels.Media;
import backend.multidbapi.multidbapi.dbmodels.User;
import backend.multidbapi.multidbapi.dbmodels.UserMedia;
import backend.multidbapi.multidbapi.dbmodels.UserMediaKey;
import backend.multidbapi.multidbapi.interfaces.MediaInterface;
import backend.multidbapi.multidbapi.models.GrantEnum;
import backend.multidbapi.multidbapi.models.MediaRequest;
import backend.multidbapi.multidbapi.models.UpdateMediaRequest;
import backend.multidbapi.multidbapi.repository.MediaRepository;
import backend.multidbapi.multidbapi.repository.UserMediaRepository;
import backend.multidbapi.multidbapi.repository.UserRepository;

@Service
public class MediaService implements MediaInterface {

    @Autowired
    public MediaRepository mediaRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public UserMediaRepository userMediaRepository;

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
    public String DownloadFile(String mediaId) throws Exception {
        Media media = mediaRepository.getReferenceById(mediaId);
        return media.getName();
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
}
