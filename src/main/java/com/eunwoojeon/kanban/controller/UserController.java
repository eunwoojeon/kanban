package com.eunwoojeon.kanban.controller;

import com.eunwoojeon.kanban.dto.DTO;
import com.eunwoojeon.kanban.service.JoinService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final JoinService joinService;

    @PostMapping("/join")
    public String join(@RequestBody DTO.JoinDTO joinDTO, HttpServletRequest request) {
        boolean result = joinService.joinProcess(joinDTO);
        if (result) {
            return "ok";
        } else {
            return "no";
        }
    }

    @PostMapping("/logintest")
    public Boolean requestLogin(@RequestBody String username, @RequestBody String password) {
        System.out.println("login");
        System.out.println(username);
        System.out.println(password);
        return true;
    }

    @GetMapping("/logout")
    public Boolean requestLogout(HttpServletRequest request) {
        System.out.println("logout");
        return true;
    }
}
