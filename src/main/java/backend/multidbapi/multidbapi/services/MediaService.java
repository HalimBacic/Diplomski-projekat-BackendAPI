package backend.multidbapi.multidbapi.services;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.multidbapi.multidbapi.dbmodels.Media;
import backend.multidbapi.multidbapi.dbmodels.User;
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
    public Media UploadFile(MediaRequest mediaRequest) throws Exception {
        User user = userRepository.getReferenceById(mediaRequest.getUsername());
        Media mediaDTO = new Media(mediaRequest.getType(),
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
    public Media PreviewFile(Long mediaId) throws Exception {
        Media metadata = mediaRepository.getReferenceById(mediaId);
        return metadata;
    }

    @Override
    public Media UpdateFile(MediaRequest mediaRequest) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'UpdateFile'");
    }

    @Override
    public Media GiveFileAccess(List<User> user, int mediaId) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'GiveFile'");
    }


    @Override
    public String DownloadFile(Long mediaId) throws Exception {
           Media media = mediaRepository.getReferenceById(mediaId);
           return media.getName();
    }

    public void saveFile(InputStream inputStream, String filePath) throws IOException {
                try (BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filePath+".jpg"))) {

            byte[] buffer = new byte[1024*1024*1024];
            int bytesRead;
            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, bytesRead);
            }
        }
    }
}
