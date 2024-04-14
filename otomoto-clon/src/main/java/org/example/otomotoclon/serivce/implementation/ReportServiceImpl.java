package org.example.otomotoclon.serivce.implementation;

import org.example.otomotoclon.entity.Announcement;
import org.example.otomotoclon.serivce.AnnouncementService;
import org.example.otomotoclon.serivce.ReportService;
import org.example.otomotoclon.util.CSVFileManager;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    private final AnnouncementService announcementService;

    public ReportServiceImpl(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @Override
    public Map<String, Long> generateReportAnnouncementsQuantityByDate(Date from, Date to) {
        if (to == null) to = new Date();
        List<Object[]> reportData = announcementService.getQuantityOfAnnouncementsByDate(from, to);
        return convertObjectArrayToLongMap(reportData);
    }

    @Override
    public Map<String, Long> generateReportAnnouncementsQuantityByBrandAndDate(String brand, Date from, Date to) {
        if (to == null) to = new Date();
        List<Object[]> reportData = announcementService.getQuantityOfAnnouncementsByBrandAndDate(brand, from, to);
        return convertObjectArrayToLongMap(reportData);
    }

    @Override
    public Resource generateAnnouncementsCSVReportByDate(Date from, Date to) throws IOException {
        if (to == null) to = new Date();
        List<Announcement> announcements = announcementService.getAnnouncementsByDates(from, to);
        byte[] reportData = generateCSV(announcements);
        return new ByteArrayResource(reportData);
//        return new ByteArrayResource(reportData, getFilenameToReport("announcementsByDate.csv"));
    }

    private byte[] generateCSV(List<Announcement> announcements) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (CSVFileManager fileManager = new CSVFileManager(outputStream)) {
            fileManager.writeHeader("Id", "Email", "Is active","Date", "Brand", "Model", "Generation", "Price", "Year", "Vin");
            for (Announcement a : announcements) {
                String generationName = a.getCar().getGeneration() != null ? a.getCar().getGeneration().getName() : "";
                fileManager.writeData(
                        String.valueOf(a.getId()),
                        a.getUser().getEmail(),
                        String.valueOf(a.isActive()),
                        String.valueOf(a.getAddedDate()),
                        a.getCar().getBrand().getName(),
                        a.getCar().getModel().getName(),
                        generationName,
                        String.format("%.2f", a.getPrice()),
                        String.valueOf(a.getCar().getYearProduction()),
                        a.getCar().getVin()
                );
            }
        }
        return outputStream.toByteArray();
    }


    private Map<String, Long> convertObjectArrayToLongMap(List<Object[]> reportData) {
        Map<String, Long> report = new HashMap<>();
        for (Object[] row : reportData) {
            String key = (String) row[0];
            Long value = (Long) row[1];
            report.put(key, value);
        }
        return report;
    }

}
