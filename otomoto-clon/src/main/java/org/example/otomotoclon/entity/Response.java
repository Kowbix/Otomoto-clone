package org.example.otomotoclon.entity;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class Response {

    private final String timestamp;
    private final String message;
    private final int httpsStatus;

    public Response(String message, int httpsStatus) {
        this.timestamp = String.valueOf(new Timestamp(System.currentTimeMillis()));
        this.message = message;
        this.httpsStatus =httpsStatus;
    }

}
