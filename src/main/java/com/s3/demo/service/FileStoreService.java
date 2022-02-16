package com.s3.demo.service;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

public interface FileStoreService {
    public void upload(String path,
                       String fileName,
                       Optional<Map<String, String>> optionalMetaData,
                       InputStream inputStream);

    public byte[] download(String path,String key);

    public String getUrl(String path,String objectKey);

    public void delete(String path,String objectKey);
}
