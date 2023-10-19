package com.greg.tempStore.repository;

import com.greg.tempStore.model.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadFileRepository extends JpaRepository<UploadedFile, Long> {

    boolean existsByFileName(String fileName);

    void deleteByFileName(String fileName);

    List<UploadedFile> findByUser(String user);

    UploadedFile findByFileName(String fileName);

}
