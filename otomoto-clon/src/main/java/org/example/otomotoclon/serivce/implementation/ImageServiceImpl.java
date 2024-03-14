package org.example.otomotoclon.serivce.implementation;

import org.example.otomotoclon.entity.Image;
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

    @Value("${aws.s3.bucket.image.folder}")
    private String IMAGE_FOLDER;

    @Value("${aws.s3.bucket.images.path}")
    private String IMAGE_PATH;

    private final S3Service s3Service;

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png");

    public ImageServiceImpl(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @Override
    public List<Image> uploadImages(List<MultipartFile> imageFiles) throws InvalidFileExtension {
        List<Image> images = new ArrayList<>();
        for(MultipartFile imageFile : imageFiles) {
            try {
                Image image = uploadImage(imageFile);
                images.add(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return images;
    }

    @Override
    public void deleteImages(List<Image> images) throws ObjectDontExistInDBException {
        for (Image image : images){
            deleteImage(image.getFilename());
        }
    }

    private Image uploadImage(MultipartFile imageFile) throws InvalidFileExtension {
        if(!isImageFile(imageFile)) {
            throw new InvalidFileExtension("Invalid image extension - " + imageFile.getOriginalFilename());
        }
        String imageName = s3Service.uploadFile(IMAGE_FOLDER, imageFile);
        Image image = new Image();
        image.setUrl(getImageUrl(imageName));
        image.setFilename(imageName);
        return image;
    }

    private void deleteImage(String filename) throws ObjectDontExistInDBException {
        s3Service.deleteFile(IMAGE_FOLDER, filename);
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
