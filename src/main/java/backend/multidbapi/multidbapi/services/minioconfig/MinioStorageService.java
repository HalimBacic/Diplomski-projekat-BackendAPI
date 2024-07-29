package backend.multidbapi.multidbapi.services.minioconfig;

import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import backend.multidbapi.multidbapi.interfaces.MinioStorageInterface;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;

@Service
public class MinioStorageService implements MinioStorageInterface{
        @Autowired
        private MinioClient minioClient;
    
        @Override
        public void uploadFile(String bucketName, String objectName, InputStream inputStream, String contentType) {
            try {
                boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
                if (!found) {
                    minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                }
                minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
                            inputStream, inputStream.available(), -1)
                            .contentType(contentType)
                            .build());
            } catch (Exception e) {
                throw new RuntimeException("Error occurred: " + e.getMessage());
            }
        }

        @Override
        public InputStream downloadFile(String objectName, Long userId) throws Exception {
            String bucketName = "user-" + userId;
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        }

        @Override
        public boolean createBucket(String bucketName) throws Exception {
            try {
                boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
                if (!found) {
                    minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                    return true;
                } else {
                    System.out.println("Bucket " + bucketName + " already exists.");
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
}
