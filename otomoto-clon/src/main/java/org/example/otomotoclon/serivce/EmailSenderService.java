package org.example.otomotoclon.serivce;

public interface EmailSenderService {

    void sendMail(String to, String subject, String content);
}
