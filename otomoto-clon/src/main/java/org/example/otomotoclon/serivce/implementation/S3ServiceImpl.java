package org.example.otomotoclon.serivce.implementation;

import com.amazonaws.services.s3.AmazonS3;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.serivce.S3Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 s3;

    public S3ServiceImpl(AmazonS3 s3) {
        this.s3 = s3;
    }

    @Override
    public String uploadFile(String bucketName, MultipartFile file) {
        String filename = setFilename(file.getOriginalFilename());
        try {
            File fileToUpload = convertMultiPartToFile(file);
            s3.putObject(bucketName, filename, fileToUpload);
            return filename;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void deleteFile(String bucketName, String filename) throws ObjectDontExistInDBException {
        if(!s3.doesObjectExist(bucketName, filename)) {
            throw new ObjectDontExistInDBException("Image with name: " + filename + " does not exists in AWS S3");
        }
        s3.deleteObject(bucketName, filename);
    }

    private String setFilename(String currentFilename) {
        return UUID.randomUUID().toString() + "_" + currentFilename;
    }

    private File convertMultiPartToFile(MultipartFile multipartFile) throws IOException {
        File convertedFile = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convertedFile;
    }
}
