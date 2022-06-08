package com.greg.tempStore.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class AmazonConfig {
    @Value("${accessKey}")
    private String accessKey;

    @Value("${secretKey}")
    private String secretKey;


    @Bean
    public AmazonS3 s3()
    {

        AWSCredentials awsCredentials= new BasicAWSCredentials(
                accessKey,
                secretKey);

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.AP_SOUTH_1)
                .build();
    }
//    final AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(
//            new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.AP_SOUTH_1).build();
}
