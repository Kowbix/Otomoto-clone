package org.example.otomotoclon.serivce;

import java.io.IOException;

public interface DescriptionFileService {

    String createAndUploadDescriptionFile(String description, long carId) throws IOException;
    String deleteDescriptionFile(String descriptionUrl);
    void updateDescriptionFile(String description);
}
