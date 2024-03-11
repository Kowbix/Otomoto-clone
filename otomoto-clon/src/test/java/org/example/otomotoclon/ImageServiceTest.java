package org.example.otomotoclon;

import org.aspectj.lang.annotation.Before;
import org.example.otomotoclon.exception.InvalidFileExtension;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ImageServiceTest {

    @Mock
    private S3Service s3Service;

    private final String bucketName = "otomotoclon-images";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUploadImages_ValidImages() throws IOException, InvalidFileExtension {
        List<MultipartFile> imageFiles = new ArrayList<>();
        imageFiles.add(createTestImage("test1.jpg"));
        imageFiles.add(createTestImage("test2.png"));

        ImageService imageService = new ImageServiceImpl(s3Service);

        List<String> imageUrls = imageService.uploadImages(imageFiles);
        assertEquals(2, imageUrls.size());
    }

    @Test
    public void testUploadImages_ValidImagesUr() throws IOException, InvalidFileExtension {
        List<MultipartFile> imageFiles = new ArrayList<>();
        imageFiles.add(createTestImage("test1.jpg"));
        imageFiles.add(createTestImage("test2.jpg"));

        when(s3Service.uploadFile(any(), any())).thenReturn("test1.jpg");

        ImageService imageService = new ImageServiceImpl(s3Service);

        List<String> imageUrls = imageService.uploadImages(imageFiles);

        for (String url : imageUrls) {
            assertEquals(null + "/test1.jpg", url);
            System.out.println("Returned URL: " + url);
        }
    }

    @Test
    public void testUploadImages_InvalidImageExtension() throws IOException, InvalidFileExtension {
        List<MultipartFile> imageFiles = new ArrayList<>();
        imageFiles.add(createTestImage("test1.gif"));
        imageFiles.add(createTestImage("test2.text"));

        ImageService imageService = new ImageServiceImpl(s3Service);

        assertThrows(InvalidFileExtension.class, () -> imageService.uploadImages(imageFiles));
    }

    private MultipartFile createTestImage(String filename) {
        return new MockMultipartFile(filename, filename, "image/jpg", new byte[10]);
    }
}
