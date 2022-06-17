package com.greg.tempStore.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.greg.tempStore.bucket.BucketName;
import com.greg.tempStore.model.UploadFile;
import com.greg.tempStore.service.UploadFileService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@RestController
@RequestMapping("/tempStore")
@CrossOrigin
public class UploadFileController {
    Logger logger = LoggerFactory.getLogger(UploadFileController.class);


    private UploadFileService uploadFileService;
    private final AmazonS3 s3;

    @Autowired
    public UploadFileController(AmazonS3 s3, UploadFileService uploadFileService) {
        this.s3 = s3;
        this.uploadFileService = uploadFileService;
    }


//    @GetMapping("/listFiles")
//    public List<UploadFile> showUploadedFiles()
//    {
//
//        return uploadFileService.getFilesList();
//    }

    @GetMapping("/listFiles/{user}")
    public List<UploadFile> showUploadedFiles(@PathVariable String user) {
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

//        System.out.println(fileName);
        S3Object downloadFile = s3.getObject(new GetObjectRequest(BucketName.TEMP_STORE.getBucketName(), fileName));
//        InputStream inputStream = downloadFile.getObjectContent();
//        InputStreamResource resource = new InputStreamResource(inputStream);
//        System.out.println("Inside download method");
        S3ObjectInputStream stream = downloadFile.getObjectContent();
        byte[] content = null;
        try {
            content = IOUtils.toByteArray(stream);
            downloadFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayResource resource = new ByteArrayResource(content);

//        logger.info("working???? " + content.length);
        fileName = fileName.substring(fileName.lastIndexOf('/') + 1).trim();
//        System.out.println(fileName);
        return ResponseEntity
                .ok()
                .contentLength(content.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);

    }

//    @GetMapping(value = "/download")
//    public void downloadFile(
//                                     @RequestParam("fileName") String fileName)
//    {
//         uploadFileService.downloadFile(fileName);
////        String dataDirectory = request.getServletContext().getRealPath("/home/gg-linux/Downloads");
////        Path file = Paths.get(dataDirectory, fileName);
////        if (Files.exists(file))
////        {
////            response.setContentType("application/OCTET_STREAM");
////            response.addHeader("Content-Disposition", "attachment; filename="+fileName);
////            try
////            {
////                Files.copy(file, response.getOutputStream());
////                response.getOutputStream().flush();
////            }
////            catch (IOException ex) {
////                ex.printStackTrace();
////            }
////        }
//    }

//    @GetMapping(value = "/download/{fileName}")
//    public ResponseEntity<ByteArrayResource> writeFileContentInResponse(HttpServletResponse response, @PathVariable String fileName) throws IOException {
//        System.out.println("Inside download method");
//        S3Object downloadFile = s3.getObject(new GetObjectRequest(BucketName.TEMP_STORE.getBucketName(), fileName));
////        InputStream inputStream = downloadFile.getObjectContent();
////        InputStreamResource resource = new InputStreamResource(inputStream);
//        System.out.println("Inside download method");
//        S3ObjectInputStream stream = downloadFile.getObjectContent();
//        byte[] content =null;
//        try {
//            content = IOUtils.toByteArray(stream);
//            downloadFile.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ByteArrayResource resource = new ByteArrayResource(content);
//        System.out.println("working???? "+content.length);
//        logger.info("working???? "+content.length);
//        return ResponseEntity
//                .ok()
//                .contentLength(content.length)
//                .header("Content-type", "application/octet-stream")
//                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
//                .body(resource);
////        return ResponseEntity.ok()
////                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
////                .contentLength(downloadFile.getObjectMetadata().getContentLength())
////                .contentType(MediaType.valueOf(MediaType.APPLICATION_PDF_VALUE))
////                .body(resource);
////        return prepareResponse( response,inputStream,fileName);
//    }

//    public HttpServletResponse prepareResponse(HttpServletResponse response, InputStream inputStream, String fileName) throws IOException {
//        response.setHeader("Content-Disposition","attachment; filename="+fileName);
//        try {
//            int c;
//            while ((c = inputStream.read()) != -1) {
//                response.getWriter().write(c);
//            }
//        }
//        catch(Exception e)
//        {
//
//            logger.info(String.valueOf(e));
//            e.printStackTrace();
//        }
//        finally {
//            if (inputStream != null)
//                inputStream.close();
//            response.getWriter().close();
//        }
//        return response;
//    }


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




