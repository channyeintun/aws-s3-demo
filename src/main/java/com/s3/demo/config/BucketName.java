package com.s3.demo.config;

public enum BucketName {

    BUCKET_NAME("your-bucket-name");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName=bucketName;
    }

    public String getBucketName() {
        return this.bucketName;
    }
}
