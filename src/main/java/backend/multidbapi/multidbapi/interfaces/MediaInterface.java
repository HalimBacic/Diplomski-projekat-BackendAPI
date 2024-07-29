package backend.multidbapi.multidbapi.interfaces;

import java.util.List;

import backend.multidbapi.multidbapi.dto.MediaDto;
import backend.multidbapi.multidbapi.dto.UserDto;
import backend.multidbapi.multidbapi.models.MediaRequest;

public interface MediaInterface {
    public MediaDto UploadFile(MediaRequest mediaRequest) throws Exception;
    public MediaDto DownloadFile(MediaRequest mediaRequest) throws Exception;
    public MediaDto UpdateFile(MediaRequest mediaRequest) throws Exception;
    public MediaDto GiveFileAccess(List<UserDto> user, int mediaId) throws Exception;
}
