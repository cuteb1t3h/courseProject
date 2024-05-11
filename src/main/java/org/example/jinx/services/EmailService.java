package org.example.jinx.services;

import org.example.jinx.models.EmailSend;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender emailSender;
    private final String receiverEmail;

    public EmailService(JavaMailSender emailSender, @Value("${mail.receiver}") String receiverEmail) {
        this.emailSender = emailSender;
        this.receiverEmail = receiverEmail;
    }

    public void sendMail(EmailSend emailSend) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(receiverEmail);
        simpleMailMessage.setSubject(emailSend.getEmail() + " | " + emailSend.getName() + " | " + emailSend.getTitle());
        simpleMailMessage.setText(emailSend.getComment());
        emailSender.send(simpleMailMessage);
    }

    public void sendForgotPassword(String receiverEmail, String newPassword) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(receiverEmail);
        simpleMailMessage.setSubject("Восстановление пароля \"Jinx\"");
        simpleMailMessage.setText("Ваш новый пароль: " + newPassword);
        emailSender.send(simpleMailMessage);
    }
}