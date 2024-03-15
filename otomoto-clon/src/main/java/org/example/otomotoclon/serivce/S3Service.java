package org.example.otomotoclon.serivce;

import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface S3Service {


    String uploadFile(String folder, MultipartFile file);
    String uploadFile(String folder, File file);
    void deleteFile(String folder, String filename);
}
