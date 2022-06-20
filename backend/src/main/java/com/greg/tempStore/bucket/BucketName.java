package com.greg.tempStore.bucket;

public enum BucketName {

    TEMP_STORE("temp-store-app");

    private final String bucketName;

    BucketName(String bucketName)
    {
        this.bucketName=bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }


}
