package com.eunwoojeon.kanban.controller;

import com.eunwoojeon.kanban.dto.DTO;
import com.eunwoojeon.kanban.service.JoinService;
import com.eunwoojeon.kanban.service.MailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;
    private final MailService mailService;

    private int number;
    private LocalDateTime expireTime;
    private boolean isVerified;

    @PostMapping("/join")
    public boolean join(@RequestBody DTO.JoinDTO joinDTO, HttpServletRequest request) {
        if (!isVerified) {
            return false;
        }

        boolean result = joinService.joinProcess(joinDTO);
        if (result) {
            return true;
        } else {
            return false;
        }
    }

    @GetMapping("/mailsend")
    public boolean mailSend(@RequestParam(value = "email") String address) {
        isVerified = false;
        boolean isDuplicated = joinService.isExistEmail(address);
        if (isDuplicated) {
            return false;
        }

        try {
            number = mailService.sendMail(address); // 인증 메일 전송
            expireTime = mailService.getExpireTime();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @GetMapping("/mailcheck")
    public ResponseEntity<?> mailCheck(@RequestParam(value = "code") String userNumber) {
        boolean isMatch = userNumber.equals(String.valueOf(number));
        boolean isExpired = expireTime.isBefore(LocalDateTime.now()); // 만료 여부

        HashMap<String, Boolean> body = new HashMap<>();
        body.put("result", isMatch && !isExpired);
        body.put("isExpired", isExpired);
        body.put("isMatch", isMatch);
        isVerified = true;

        return ResponseEntity.ok(body);
    }
}
