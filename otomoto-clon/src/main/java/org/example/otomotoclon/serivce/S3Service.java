package org.example.otomotoclon.serivce;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {


    String uploadFile(String bucketName, MultipartFile file);
    void deleteFile(String bucketName, String filename);
}
