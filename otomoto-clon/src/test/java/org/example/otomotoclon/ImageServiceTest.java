package org.example.otomotoclon;

import org.aspectj.lang.annotation.Before;
import org.example.otomotoclon.entity.Image;
import org.example.otomotoclon.exception.InvalidFileExtension;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.serivce.ImageService;
import org.example.otomotoclon.serivce.S3Service;
import org.example.otomotoclon.serivce.implementation.ImageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ImageServiceTest {

    @Mock
    private S3Service s3Service;

    private final String bucketName = "otomotoclon-images";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCountUploadImages_ValidImages() throws InvalidFileExtension {
        List<MultipartFile> imageFiles = new ArrayList<>();
        imageFiles.add(createTestImage("test1.jpg"));
        imageFiles.add(createTestImage("test2.png"));

        ImageService imageService = new ImageServiceImpl(s3Service);

        List<Image> images = imageService.uploadImages(imageFiles);
        assertEquals(2, images.size());
    }

    @Test
    public void testUploadImages_ValidImages() throws IOException, InvalidFileExtension {
        List<MultipartFile> imageFiles = new ArrayList<>();
        String fileName1 = "test1.jpg";

        imageFiles.add(createTestImage(fileName1));

        when(s3Service.uploadFile(null, imageFiles.get(0))).thenReturn("test1.jpg");

        ImageService imageService = new ImageServiceImpl(s3Service);

        List<Image> images = imageService.uploadImages(imageFiles);

        for (Image image : images) {
            assertEquals(null + "/" + fileName1, image.getUrl());
            System.out.println("Returned URL: " + image.getUrl());
        }
    }

    private MultipartFile createTestImage(String filename) {
        return new MockMultipartFile(filename, filename, "image/jpg", new byte[10]);
    }
}
