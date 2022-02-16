package com.s3.demo.service.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.s3.demo.config.BucketName;
import com.s3.demo.service.FileStoreService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
public class FileStoreServiceImpl implements FileStoreService {

    private final AmazonS3 amazonS3;

    public FileStoreServiceImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public void upload(String path,
                       String fileName,
                       Optional<Map<String, String>> optionalMetaData,
                       InputStream inputStream) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        optionalMetaData.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(objectMetadata::addUserMetadata);
            }
        });
        try {
            amazonS3.putObject(path, fileName, inputStream, objectMetadata);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to upload the file", e);
        }
    }

    @Override
    public byte[] download(String path, String key) {
        try {
            S3Object object = amazonS3.getObject(path, key);
            S3ObjectInputStream objectContent = object.getObjectContent();
            return IOUtils.toByteArray(objectContent);
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to download the file", e);
        }
    }

    @Override
    public String getUrl(String path, String objectKey) {
        try {
            String url = amazonS3.getUrl(path, objectKey).toString();
            return url;
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to get url.");
        }
    }

    @Override
    public void delete(String path, String objectKey) {
        String key = path + "/" + objectKey;
        try {
            amazonS3.deleteObject(
                    new DeleteObjectRequest(
                            BucketName.BUCKET_NAME.getBucketName(), key
                    ));
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("Failed to delete image");
        }
    }
}
