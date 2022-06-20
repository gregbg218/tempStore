package com.greg.tempStore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;


//@Entity
public class UploadFile {


//        @Id
//        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        private int id;

        private String fileName;


        private String fileSize;
        private String uploadTime;

    public UploadFile() {
    }

    public UploadFile( String fileName, String fileSize, String uploadTime) {

        this.fileName = fileName;
        this.fileSize = fileSize;
        this.uploadTime = uploadTime;
    }


    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

//    public int getId() {
//        return id;
//    }
//
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

