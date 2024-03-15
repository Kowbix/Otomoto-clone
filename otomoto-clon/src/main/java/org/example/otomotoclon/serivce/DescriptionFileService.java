package org.example.otomotoclon.serivce;

import java.io.IOException;

public interface DescriptionFileService {

    String createAndUploadDescriptionFile(String description, long carId) throws IOException;
    void deleteDescriptionFile(String descriptionUrl);
    String updateDescriptionFile(String descriptionUrl, String updatedDescription, long cardId) throws IOException;
}
