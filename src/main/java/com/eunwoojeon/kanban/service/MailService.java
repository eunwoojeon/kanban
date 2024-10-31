package com.eunwoojeon.kanban.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class MailService {

    ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

    private final JavaMailSender javaMailSender;
    private static final String senderEmail = "SimpleKanban@gmail.com";
    private static int number;

    public static void createNumber() {
        number = (int) (Math.random() * (90000)) + 100000; // 100,000 ~ 999,999
    }

    public LocalDateTime getExpireTime() {
        return LocalDateTime.now().plusMinutes(5); // 만료 시간 5분
    }

    public MimeMessage CreateMail(String address) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, address);
            message.setSubject("Simple Kanban 이메일 인증");

            String body = "";
            body += "<h3>" + "이메일 인증 코드를 입력하세요." + "</h3>";
            body += "<h1>" + number + "</h1>";
            body += "<h3>" + "인증 코드는 5분 뒤 만료됩니다. 감사합니다." + "</h3>";
            message.setText(body, "UTF-8", "html");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return message;
    }

    public int sendMail(String address) {
        createNumber();
        executorService.execute(() -> {
            MimeMessage message = CreateMail(address);
            javaMailSender.send(message);
        });

        return number;
    }
}
