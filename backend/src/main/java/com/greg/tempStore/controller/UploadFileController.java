package com.greg.tempStore.controller;



import com.greg.tempStore.model.UploadedFile;
import com.greg.tempStore.service.UploadFileService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/tempStore")
@CrossOrigin
public class UploadFileController {
    Logger logger = LoggerFactory.getLogger(UploadFileController.class);


    private UploadFileService uploadFileService;


    @Autowired
    public UploadFileController( UploadFileService uploadFileService) {

        this.uploadFileService = uploadFileService;
    }



    @GetMapping("/listFiles/{user}")
    public List<UploadedFile> showUploadedFiles(@PathVariable String user) {
        System.out.println("Inside LIST method");
        return uploadFileService.getFilesListUser(user);
    }


    @PostMapping(path = "/upload/{user}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void uploadFile(@RequestParam("file") MultipartFile file, @PathVariable String user) {


        uploadFileService.uploadFile(file, user);

    }

    @GetMapping(value = "/createUser", produces = MediaType.TEXT_PLAIN_VALUE)
    public String createUser() {
        String newId = RandomStringUtils.randomAlphanumeric(4);
        return newId;

    }

    @GetMapping(value = "/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("fileName") String fileName) {


        ByteArrayResource resource = uploadFileService.downloadFile(fileName);
        long contentLength = resource.contentLength();


        fileName = fileName.substring(fileName.lastIndexOf('/') + 1).trim();
//        System.out.println(fileName);
        return ResponseEntity
                .ok()
                .contentLength(contentLength)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);

    }





    @GetMapping(value = "/delete")
    public void deleteFile(
            @RequestParam("fileName") String fileName) {
        uploadFileService.deleteFile(fileName);
    }

    @GetMapping(value = "/deleteAll")
    public void deleteFile() {
        uploadFileService.deleteAllFiles();
    }

}




