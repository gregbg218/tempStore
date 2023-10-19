package com.greg.tempStore.model;

import javax.persistence.*;
import java.util.Arrays;


@Entity
@Table(name = "uploaded_files")
public class UploadedFile {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String fileName;

        @Lob
        private byte[] fileData;

        private String fileSize;
        private String uploadTime;

        private String user;

    public UploadedFile() {
    }

    public UploadedFile(Long id, String fileName, byte[] fileData, String fileSize, String uploadTime,String user) {
        this.id = id;
        this.fileName = fileName;
        this.fileData = fileData;
        this.fileSize = fileSize;
        this.uploadTime = uploadTime;
        this.user = user;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UploadedFile{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", fileData=" + Arrays.toString(fileData) +
                ", fileSize='" + fileSize + '\'' +
                ", uploadTime='" + uploadTime + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}

