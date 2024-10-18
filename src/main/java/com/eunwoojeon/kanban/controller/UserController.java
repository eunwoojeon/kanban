package com.eunwoojeon.kanban.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/kanban/user")
public class UserController {
    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public void requestLoginTest(HttpServletRequest request) {
        System.out.println("login");
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET})
    public void requestLogoutTest(HttpServletRequest request) {

    }
}
