package com.eunwoojeon.kanban.controller;

import com.eunwoojeon.kanban.dto.DTO;
import com.eunwoojeon.kanban.service.JoinService;
import com.eunwoojeon.kanban.service.MailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
    public String join(@RequestBody DTO.JoinDTO joinDTO, HttpServletRequest request) {
        if (!isVerified) {
            return "no";
        }
        boolean result = joinService.joinProcess(joinDTO);
        if (result) {
            return "ok";
        } else {
            return "no";
        }
    }

    @PostMapping("/mailsend")
    public HashMap<String, Object> mailSend(@RequestParam String address) {
        HashMap<String, Object> map = new HashMap<>();
        isVerified = false;

        try {
            number = mailService.sendMail(address); // 인증 메일 전송
            String num = String.valueOf(number);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        expireTime = mailService.getExpireTime();
        return map;
    }

    @GetMapping("/mailcheck")
    public ResponseEntity<?> mailCheck(@RequestParam String userNumber) {
        boolean isMatch = userNumber.equals(String.valueOf(number));
        boolean isExpired = expireTime.isBefore(LocalDateTime.now()); // 만료 여부

        HashMap<String, Boolean> body = new HashMap<>();
        body.put("ISMATCH", isMatch);
        body.put("ISEXPIRED", isExpired);
        isVerified = true;

        return ResponseEntity.ok(body);
    }
}
