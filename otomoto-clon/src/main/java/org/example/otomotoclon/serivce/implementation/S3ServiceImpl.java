package org.example.otomotoclon.serivce.implementation;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.serivce.S3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 s3;

    @Value("${aws.s3.bucket.name}")
    private String BUCKET_NAME;

    public S3ServiceImpl(AmazonS3 s3) {
        this.s3 = s3;
    }

    @Override
    public String uploadFile(String folder, MultipartFile file) {
        String filename = setFilename(file.getOriginalFilename());
        try {
            File fileToUpload = convertMultiPartToFile(file);
            s3.putObject(BUCKET_NAME, folder + filename, fileToUpload);
            return filename;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public String uploadFile(String folder, File file) {
        String filename = setFilename(file.getName());
        s3.putObject(BUCKET_NAME, folder + filename, file);
        return filename;
    }

    @Override
    public void deleteFile(String folder, String filename) throws ObjectDontExistInDBException {
        if(!s3.doesObjectExist(BUCKET_NAME, folder + filename)) {
            throw new ObjectDontExistInDBException("File with name: " + folder + filename + " does not exists in bucket: " + BUCKET_NAME);
        }
        s3.deleteObject(BUCKET_NAME, folder + filename);
    }

    @Override
    public S3Object getFile(String folder, String filename) throws ObjectDontExistInDBException {
        if(!s3.doesObjectExist(BUCKET_NAME, folder + filename)) {
            throw new ObjectDontExistInDBException("File with name: " + folder + filename + " does not exists in bucket: " + BUCKET_NAME);
        }
        return s3.getObject(BUCKET_NAME, folder + filename);
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
