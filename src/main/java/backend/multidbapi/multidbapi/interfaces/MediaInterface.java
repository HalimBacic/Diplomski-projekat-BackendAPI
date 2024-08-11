package backend.multidbapi.multidbapi.interfaces;

import java.util.List;
import backend.multidbapi.multidbapi.dbmodels.Media;
import backend.multidbapi.multidbapi.models.GrantEnum;
import backend.multidbapi.multidbapi.models.MediaRequest;
import backend.multidbapi.multidbapi.models.UpdateMediaRequest;

public interface MediaInterface {
    public Media UploadFile(MediaRequest mediaRequest) throws Exception;
    public Media PreviewFile(String mediaRequest) throws Exception;
 //   public List<Media> SearchFiles(String term) throws Exception;
    public String DownloadFile(String mediaId) throws Exception;
    public Media UpdateFile(String mediaId, UpdateMediaRequest mediaRequest) throws Exception;
    public Boolean GiveFileAccess(List<String> user, String owner, String mediaId, GrantEnum grant) throws Exception;
    public Boolean RestrictFileAccess(List<String> user, String owner, String mediaId) throws Exception;
    public String DeleteFile(String id);
}
