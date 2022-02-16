package com.s3.demo.service.impl;

import com.s3.demo.config.BucketName;
import com.s3.demo.service.FileStoreService;
import com.s3.demo.service.ImageUploadService;
import com.s3.demo.util.FileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageUploadServiceImpl implements ImageUploadService {

    @Autowired
    FileStoreService fileStore;

    @Override
    public String uploadImage(MultipartFile file) {
        //validate empty or image type
        FileValidator.validateFile(file);
        //get file metadata
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        //Save Image in S3
        String path = String.format("%s/%s", BucketName.BUCKET_NAME.getBucketName(), UUID.randomUUID());
        String fileName = String.format("%s", file.getOriginalFilename());
        try {
            fileStore.upload(path, fileName, Optional.of(metadata), file.getInputStream());
            String url = fileStore.getUrl(path, fileName);
            System.out.println("image url : " + url);
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Failed to upload file", e);
        }
    }
}
