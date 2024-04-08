package org.example.otomotoclon.serivce.implementation;

import org.example.otomotoclon.dto.announcement.AnnouncementDTO;
import org.example.otomotoclon.entity.SubscribedCar;
import org.example.otomotoclon.serivce.AnnouncementService;
import org.example.otomotoclon.serivce.EmailSenderService;
import org.example.otomotoclon.serivce.NotificationSubscribedCarService;
import org.example.otomotoclon.serivce.SubscribedCarService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@EnableScheduling
public class NotificationSubscribedCarServiceImpl implements NotificationSubscribedCarService {

    private final SubscribedCarService subscribedCarService;
    private final AnnouncementService announcementService;
    private final EmailSenderService emailSenderService;
    private final TemplateEngine templateEngine;
    @Value("${subscription.car.mail.template}")
    private String EMAIL_TEMPLATE;

    public NotificationSubscribedCarServiceImpl(SubscribedCarService subscribedCarService, AnnouncementService announcementService, EmailSenderService emailSenderService, TemplateEngine templateEngine) {
        this.subscribedCarService = subscribedCarService;
        this.announcementService = announcementService;
        this.emailSenderService = emailSenderService;
        this.templateEngine = templateEngine;
    }

    @Override
    @Scheduled(cron = "0 0 10 * * *")
    public void sendEmailsWithNewAnnouncementsToSubscribers() {
        List<SubscribedCar> subscriptions = subscribedCarService.getAllSubscribedCar();
        Date yesterday = getYesterdayDate();
        List<AnnouncementDTO> announcements;
        for (SubscribedCar subscription : subscriptions) {
            announcements = announcementService.getAnnouncementsByAddedDateAndCarInfo(
                    yesterday,
                    subscription.getBrand().getName(),
                    subscription.getModel().getName(),
                    subscription.getGeneration().getName()
            );
            if (!announcements.isEmpty()) {
                String context = prepareContext(announcements);
                String SUBJECT = "New announcements of ";
                emailSenderService.sendMail(
                        subscription.getUser().getEmail(),
                        SUBJECT + subscription.getBrand().getName(),
                        context
                );
            }
        }
    }

    private String prepareContext(List<AnnouncementDTO> announcements) {
        Context context = new Context();
        context.setVariable("announcements", announcements);
        return templateEngine.process(EMAIL_TEMPLATE, context);
    }

    private static Date getYesterdayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }


}
