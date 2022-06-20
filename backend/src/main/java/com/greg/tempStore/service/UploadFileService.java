package com.greg.tempStore.service;



import com.greg.tempStore.filestore.FileStore;
import com.greg.tempStore.model.UploadFile;
//import com.greg.tempStore.repository.UploadFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UploadFileService {
    Logger logger = LoggerFactory.getLogger(UploadFileService.class);

    private FileStore fileStore;

    @Autowired
    public UploadFileService(FileStore fileStore) {
        this.fileStore=fileStore;
    }

//    public List<UploadFile> getFilesList()
//    {
//        return fileStore.listFiles();
//    }

    public List<UploadFile> getFilesListUser(String user)
    {
        return fileStore.listFilesUser(user);
    }

    public void uploadFile(MultipartFile file,String user)
    {
        if(file.isEmpty())
            throw new IllegalStateException("Cannot upload empty file ["+file.getSize()+"]");

        Map<String ,String> metaData = new HashMap<>();
        metaData.put("Content-Type", file.getContentType());
//        logger.info(String.valueOf(file.getContentType()));
        metaData.put("Content-Length", String.valueOf(file.getSize()));

//        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(),file.getOriginalFilename());

        String fileName = user+"/"+String.format("%s",file.getOriginalFilename());

        try{
            fileStore.save(fileName, Optional.of(metaData),file.getInputStream());
//            uploadFile.setUserProfileImageLink(fileName);
        }
        catch (IOException io)
        {
            throw new IllegalStateException(io);
        }


//        uploadFileRepository.save(uploadFile);

    }

//    public ResponseEntity<InputStreamResource> downloadFile(String fileName)
//    {
//      return fileStore.downloadFile(fileName);
//    }

    public void downloadFile(String fileName)
    {
         fileStore.downloadFile(fileName);
    }

    public void deleteFile(String fileName)
    {
        fileStore.deleteFile(fileName);
    }

    public void deleteAllFiles() {
        fileStore.deleteAllFiles();
    }
}
