package com.indigo.service;


import com.sendgrid.*;

import com.sendgrid.helpers.mail.Mail;

import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    public void sendEmail(String to, String subject, String body) throws Exception {
        Email from = new Email("shakshiashti2@gmail.com");
        Email toEmail = new Email(to);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, toEmail, content);

        SendGrid sg = new SendGrid(sendGridApiKey);
        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sg.api(request);
        if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
            System.out.println("Email sent successfully");
        } else {
            System.out.println("Failed to send email. Response: " + response.getBody());
        }
    }
}

