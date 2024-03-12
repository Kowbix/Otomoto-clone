package org.example.otomotoclon.serivce;

import org.example.otomotoclon.entity.Image;
import org.example.otomotoclon.exception.InvalidFileExtension;
import org.example.otomotoclon.exception.ObjectDontExistInDBException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    List<Image> uploadImages(List<MultipartFile> files) throws InvalidFileExtension;
    void deleteImages(List<Image> images) throws ObjectDontExistInDBException;
}
