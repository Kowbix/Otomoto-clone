package org.example.otomotoclon.serivce;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

public interface ReportService {
    Map<String, Long> generateReportAnnouncementsQuantityByDate(Date from, Date to);
    Map<String, Long> generateReportAnnouncementsQuantityByBrandAndDate(String brand, Date from, Date to);
    Resource generateAnnouncementsCSVReportByDate(Date from, Date to) throws IOException;
}
