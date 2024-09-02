package backend.multidbapi.multidbapi.interfaces;

import java.io.InputStream;

public interface MinioStorageInterface  {
    void uploadFile(String bucketName, String objectName, InputStream inputStream, String contentType);
    void uploadThumbail(String bucketName, String objectName, InputStream inputStream, String contentType);
    boolean createBucket(String bucketName) throws Exception;
    InputStream downloadFile(String objectName, String userId) throws Exception;
    InputStream previewFile(String objectName, String userId) throws Exception;
    boolean deleteFile(String bucketName, String mediaName);
    String getPresignedUrl(String bucketName, String objectName);
}
