package backend.multidbapi.multidbapi.interfaces;

import java.io.InputStream;

public interface MinioStorageInterface  {
    void uploadFile(String bucketName, String objectName, InputStream inputStream, String contentType);
    boolean createBucket(String bucketName) throws Exception;
    InputStream downloadFile(String objectName, String userId) throws Exception;
}
