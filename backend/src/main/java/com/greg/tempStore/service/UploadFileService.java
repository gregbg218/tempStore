package com.greg.tempStore.service;




import com.greg.tempStore.model.UploadedFile;
//import com.greg.tempStore.repository.UploadFileRepository;
import com.greg.tempStore.repository.UploadFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UploadFileService {
    Logger logger = LoggerFactory.getLogger(UploadFileService.class);




    private final UploadFileRepository uploadFileRepository;

    @Autowired
    public UploadFileService(UploadFileRepository uploadFileRepository) {
        this.uploadFileRepository=uploadFileRepository;

    }



    public List<UploadedFile> getFilesListUser(String user)
    {
        return uploadFileRepository.findByUser(user) ;
    }

    public void uploadFile(MultipartFile file,String user)
    {
        if(file.isEmpty())
            throw new IllegalStateException("Cannot upload empty file ["+file.getSize()+"]");

        String fileName = user+"/"+String.format("%s",file.getOriginalFilename());

        UploadedFile uploadedFile = new UploadedFile();




        try{
            uploadedFile.setFileName(fileName);
            uploadedFile.setUser(user);
            uploadedFile.setFileData(file.getBytes());

            String fileSizeInUnits = calculateFileSize(file.getSize());
            uploadedFile.setFileSize(fileSizeInUnits);
            uploadedFile.setUploadTime(String.valueOf(LocalDateTime.now()));
            uploadFileRepository.save(uploadedFile);

        }
        catch (IOException io)
        {
            throw new IllegalStateException(io);
        }




    }


    public ByteArrayResource downloadFile(String fileName)
    {
        UploadedFile uploadedFile = uploadFileRepository.findByFileName(fileName);

        if (uploadedFile != null)
        {
            byte[] fileData = uploadedFile.getFileData();


            return new ByteArrayResource(fileData);
            }
        return null;
    }

    public void deleteFile(String fileName)
    {
        if (uploadFileRepository.existsByFileName(fileName))
        {
            uploadFileRepository.deleteByFileName(fileName);
        }
    }

    public void deleteAllFiles() {
        uploadFileRepository.deleteAll();
    }


    public String calculateFileSize(long size_bytes)
    {
        String size;
        double size_kb = size_bytes /1024;
        double size_mb = size_kb / 1024;
        double size_gb = size_mb / 1024 ;

        if (size_gb > 1){
            size = String.format("%.2f", size_gb) + " GB";
        }else if(size_mb > 1){
            size = String.format("%.2f", size_mb) + " MB";
        }else{
            size = String.format("%.2f", size_kb) + " KB";
        }
        return size;
    }
}
