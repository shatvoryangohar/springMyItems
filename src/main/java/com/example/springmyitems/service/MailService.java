package com.example.springmyitems.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {


    private final MailSender mailSender;


    public void sendMail(String toEmail,String subject, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message);

       new Thread(new Runnable() {
           @Override
           public void run() {
               mailSender.send(simpleMailMessage);
           }
       }).start();




    }

}
