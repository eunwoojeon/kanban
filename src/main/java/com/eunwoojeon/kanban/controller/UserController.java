package com.eunwoojeon.kanban.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

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
