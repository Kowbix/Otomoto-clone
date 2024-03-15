package org.example.otomotoclon.serivce.implementation;

import org.example.otomotoclon.serivce.DescriptionFileService;
import org.example.otomotoclon.serivce.S3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class DescriptionFileServiceImpl implements DescriptionFileService {


    @Value("${aws.s3.bucket.description.folder}")
    private String DESCRIPTION_FOLDER;

    @Value("${aws.s3.bucket.description.path}")
    private String DESCRIPTION_PATH;

    private final S3Service s3Service;

    public DescriptionFileServiceImpl(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @Override
    public String createAndUploadDescriptionFile(String description, long carId) throws IOException {
        File descriptionFile = createTemporaryFile(carId);
        FileWriter writer = new FileWriter(descriptionFile);
        writer.write(description);
        writer.close();
        String filename = s3Service.uploadFile(DESCRIPTION_FOLDER, descriptionFile);
        descriptionFile.delete();
        return getDescriptionUrl(filename);
    }

    @Override
    public String deleteDescriptionFile(String descriptionUrl) {
        return null;
    }

    @Override
    public void updateDescriptionFile(String description) {

    }

    private File createTemporaryFile(long carId) throws IOException {
        File temporaryDescriptionFile = File.createTempFile("0" + Long.toString(carId), ".txt");
        return temporaryDescriptionFile;
    }

    private String getDescriptionUrl(String filename) {
        return DESCRIPTION_PATH + "/" + filename;
    }
}
