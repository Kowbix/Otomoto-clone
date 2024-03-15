package org.example.otomotoclon.serivce;

import com.amazonaws.services.s3.model.S3Object;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface S3Service {


    String uploadFile(String folder, MultipartFile file);
    String uploadFile(String folder, File file);
    void deleteFile(String folder, String filename);
    S3Object getFile(String folder, String filename);
}
