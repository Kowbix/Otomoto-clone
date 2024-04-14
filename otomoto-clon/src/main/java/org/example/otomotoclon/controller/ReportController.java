package org.example.otomotoclon.controller;

import lombok.RequiredArgsConstructor;
import org.example.otomotoclon.entity.Response;
import org.example.otomotoclon.serivce.ReportService;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/by-date/")
    public Map<String, Long> getReportByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
        return reportService.generateReportAnnouncementsQuantityByDate(from, to);
    }

    @GetMapping("/by-brand-and-date/")
    public Map<String, Long> getReportByBrandAndDate(@RequestParam String brand,
                                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
        return reportService.generateReportAnnouncementsQuantityByBrandAndDate(brand, from, to);
    }

    @GetMapping("/csv-by-date/")
    public ResponseEntity<?> getCSVReportByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
                                             @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date to) {
        try {
            Resource resource = reportService.generateAnnouncementsCSVReportByDate(from, to);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "report.csv");
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }

    }

}
