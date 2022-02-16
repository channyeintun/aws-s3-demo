package com.s3.demo.endpoint;

import com.s3.demo.service.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadEndpoint {

    @Autowired
    ImageUploadService imageUploadService;

    @PostMapping("/upload")
    public ResponseEntity<?> updateProfilePicture(@RequestParam("image") MultipartFile file) {

        String url = imageUploadService.uploadImage(file);
        return ResponseEntity.ok(url);
    }
}
