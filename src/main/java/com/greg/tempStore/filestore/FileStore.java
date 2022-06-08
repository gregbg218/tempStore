package com.greg.tempStore.filestore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.greg.tempStore.bucket.BucketName;
import com.greg.tempStore.model.UploadFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.*;

@Service
public class FileStore {
    Logger logger = LoggerFactory.getLogger(FileStore.class);

    private final AmazonS3 s3;

    @Autowired
    public FileStore(AmazonS3 s3) {
        this.s3 = s3;
    }

    public void save( String fileName, Optional<Map<String, String>> optionalMetadata, InputStream inputStream) {
        ObjectMetadata metadata = new ObjectMetadata();
        optionalMetadata.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(metadata::addUserMetadata);
            }
        });
        try {

            s3.putObject(BucketName.TEMP_STORE.getBucketName(), fileName, inputStream, metadata);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("failed to store file", e);
        }
    }

    public void downloadFile(String fileName) {
        byte[] data = null;
        String downloadFileType = null;
        InputStreamResource resource = null;
        S3Object downloadFile = null;
        String home = System.getProperty("user.home");
        File file = new File(home+"/Downloads/" + fileName);
        try {

            s3.getObject(new GetObjectRequest("temp-store-app", fileName),file);




//            downloadFile = s3.getObject(new GetObjectRequest(BucketName.TEMP_STORE.getBucketName(), fileName));
//            downloadFileType=downloadFile.getObjectMetadata().getContentType();
//            InputStream inputStream = downloadFile.getObjectContent();
//            data = IOUtils.toByteArray(inputStream);
//            resource = new InputStreamResource(inputStream);
//                    return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
//                .contentType(MediaType.parseMediaType(downloadFileType))
//                .contentLength(downloadFile.getObjectMetadata().getContentLength())
//                .body(resource);

        } catch (AmazonServiceException e) {
            throw new IllegalStateException("failed to download file", e);
        }

//        return resource;
//        logger.info(String.valueOf(MediaType.parseMediaType(downloadFileType)));
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
//                .contentType(MediaType.parseMediaType(downloadFileType))
//                .contentLength(downloadFile.getObjectMetadata().getContentLength())
//                .body(resource);

//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
//        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//        headers.add("Pragma", "no-cache");
//        headers.add("Expires", "0");
//
//
//        ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(downloadFile.getObjectMetadata().getContentLength()).contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
//        return responseEntity;

    }

    public void deleteFile(String fileName) {
        s3.deleteObject(BucketName.TEMP_STORE.getBucketName(), fileName);
    }

    public void deleteAllFiles() {
        ArrayList<DeleteObjectsRequest.KeyVersion> keys = new ArrayList<DeleteObjectsRequest.KeyVersion>();
        ObjectListing response = s3.listObjects(BucketName.TEMP_STORE.getBucketName());
        List<S3ObjectSummary> objects = response.getObjectSummaries();

        ListIterator<S3ObjectSummary> listIterator = objects.listIterator();
        while (listIterator.hasNext()) {
            S3ObjectSummary object = listIterator.next();
            keys.add(new DeleteObjectsRequest.KeyVersion(object.getKey()));
        }

        DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest(BucketName.TEMP_STORE.getBucketName())
                .withKeys(keys)
                .withQuiet(false);
        s3.deleteObjects(multiObjectDeleteRequest);
    }



    public List<UploadFile> listFilesUser(String user) {

//        logger.info(user);
//        logger.info("hey"+user);
        List<UploadFile> uploadedFiles= new ArrayList<UploadFile>() ;

        ObjectListing response = s3.listObjects(BucketName.TEMP_STORE.getBucketName());
        List<S3ObjectSummary> objects = response.getObjectSummaries();

        ListIterator<S3ObjectSummary> listIterator = objects.listIterator();

        while (listIterator.hasNext()) {
            S3ObjectSummary object = listIterator.next();
            ObjectMetadata objectMetadata=s3.getObject(new GetObjectRequest(BucketName.TEMP_STORE.getBucketName(), object.getKey())).getObjectMetadata();
            if(object.getKey().contains(user))
                uploadedFiles.add(new UploadFile(object.getKey(),calculateFileSize(object.getSize()),String.valueOf(objectMetadata.getLastModified())));
        }
        return uploadedFiles;

    }

    public String calculateFileSize(long size_bytes)
    {
        String size;
        double size_kb = size_bytes /1024;
        double size_mb = size_kb / 1024;
        double size_gb = size_mb / 1024 ;

        if (size_gb > 1){
            size = String.format("%.3f", size_gb) + " GB";
        }else if(size_mb > 1){
            size = String.format("%.3f", size_mb) + " MB";
        }else{
            size = String.format("%.3f", size_kb) + " KB";
        }
        return size;
    }

}


