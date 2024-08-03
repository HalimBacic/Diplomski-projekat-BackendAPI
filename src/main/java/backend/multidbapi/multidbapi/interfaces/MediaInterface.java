package backend.multidbapi.multidbapi.interfaces;

import java.util.List;

import backend.multidbapi.multidbapi.dbmodels.Media;
import backend.multidbapi.multidbapi.dbmodels.User;
import backend.multidbapi.multidbapi.models.MediaRequest;

public interface MediaInterface {
    public Media UploadFile(MediaRequest mediaRequest) throws Exception;
    public Media PreviewFile(Long mediaRequest) throws Exception;
    public String DownloadFile(Long mediaId) throws Exception;
    public Media UpdateFile(MediaRequest mediaRequest) throws Exception;
    public Media GiveFileAccess(List<User> user, int mediaId) throws Exception;
}
