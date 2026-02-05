package dev.filipe.user.dto;

import java.util.UUID;

public class EmailDto {
    private UUID userId;
    private String emailTo;
    private String emailSubject;
    private String body;

    public EmailDto(UUID userId, String emailTo, String emailSubject, String body) {
        this.userId = userId;
        this.emailTo = emailTo;
        this.emailSubject = emailSubject;
        this.body = body;
    }

    public EmailDto() {

    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
