package com.eunwoojeon.kanban.controller;

import com.eunwoojeon.kanban.dto.DTO.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    @PostMapping("/login")
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
