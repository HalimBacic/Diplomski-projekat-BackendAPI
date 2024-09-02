package backend.multidbapi.multidbapi.services.minioconfig;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.commons.io.input.TeeInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import backend.multidbapi.multidbapi.interfaces.MinioStorageInterface;
import backend.multidbapi.multidbapi.models.MediaTypeEnum;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.http.Method;
import net.coobird.thumbnailator.Thumbnails;

@Service
public class MinioStorageService implements MinioStorageInterface {
    @Autowired
    private MinioClient minioClient;

    @Override
    public void uploadFile(String bucketName, String objectName, InputStream inputStream, String contentType) {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            MediaTypeEnum mediaType = MediaTypeEnum.valueOf(contentType);

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType(mediaType.getMimeType())
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred: " + e.getMessage());
        }
    }

    @Override
    public InputStream downloadFile(String objectName, String userName) throws Exception {
        String bucketName = "user-" + userName;
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build());
    }

    @Override
    public InputStream previewFile(String objectName, String userName) throws Exception {
        String bucketName = "user-" + userName;
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object("thumbnails/" + objectName)
                        .build());
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

    @Override
    public boolean deleteFile(String bucketName, String mediaName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(mediaName).build());
        } catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
                | InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
                | IllegalArgumentException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void uploadThumbail(String bucketName, String objectName, InputStream inputStream, String contentType) {
        ByteArrayOutputStream thumbnailOutputStream = new ByteArrayOutputStream();
        try {
            Thumbnails.of(inputStream)
                    .size(150, 150)
                    .outputFormat("jpg")
                    .toOutputStream(thumbnailOutputStream);

            // Konvertovanje u InputStream
            InputStream thumbnailInputStream = new ByteArrayInputStream(thumbnailOutputStream.toByteArray());
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object("thumbnails/" + objectName)
                            .stream(thumbnailInputStream, thumbnailOutputStream.size(), -1)
                            .contentType("image/jpeg")
                            .build());
        } catch (IOException | InvalidKeyException | ErrorResponseException | InsufficientDataException
                | InternalException | InvalidResponseException | NoSuchAlgorithmException | ServerException
                | XmlParserException | IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public String getPresignedUrl(String bucketName, String objectName) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(60 * 60) // URL važi 1 sat
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while generating pre-signed URL: " + e.getMessage());
        }
    }
}
