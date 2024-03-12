package org.example.otomotoclon.serivce.implementation;

import org.example.otomotoclon.exception.InvalidFileExtension;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.example.otomotoclon.serivce.ImageService;
import org.example.otomotoclon.serivce.S3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {


    @Value("${aws.s3.bucket.images.name}")
    private String BUCKET_NAME;

    @Value("${aws.s3.bucket.images.path}")
    private String IMAGE_PATH;

    private final S3Service s3Service;

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png");

    public ImageServiceImpl(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @Override
    public List<String> uploadImages(List<MultipartFile> imageFiles) throws InvalidFileExtension {
        List<String> imageUrls = new ArrayList<>();
        for(MultipartFile image : imageFiles) {
            try {
                String imageUrl = uploadImage(image);
                imageUrls.add(imageUrl);
            } catch (InvalidFileExtension e) {
                throw new InvalidFileExtension(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return imageUrls;
    }

    @Override
    public void deleteImages(List<String> imageNames) throws ObjectDontExistInDBException {
        for (String image : imageNames){
            try {
                deleteImage(image);
            } catch (ObjectDontExistInDBException e) {
                throw new ObjectDontExistInDBException(e.getMessage());
            }
        }
    }

    private String uploadImage(MultipartFile imageFile) throws InvalidFileExtension {
        if(!isImageFile(imageFile)) {
            throw new InvalidFileExtension("Invalid image extension - " + imageFile.getOriginalFilename());
        }
        String imageName = s3Service.uploadFile(BUCKET_NAME, imageFile);
        return getImageUrl(imageName);
    }

    private void deleteImage(String filename) throws ObjectDontExistInDBException {
        try {
            s3Service.deleteFile(BUCKET_NAME, filename);
        } catch (ObjectDontExistInDBException e) {
            throw new ObjectDontExistInDBException(e.getMessage());
        }
    }

    private boolean isImageFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if(filename == null) {
            return false;
        }
        String fileExtension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
        return ALLOWED_EXTENSIONS.contains(fileExtension);
    }

    private String getImageUrl(String imageName) {
        return IMAGE_PATH + "/" + imageName;
    }
}
