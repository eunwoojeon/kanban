package com.eunwoojeon.kanban.controller;

import com.eunwoojeon.kanban.dto.DTO;
import com.eunwoojeon.kanban.service.JoinService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/kanban/user")
public class UserController {

    @Autowired
    private JoinService joinService;

    @RequestMapping(value = "/join", method = {RequestMethod.POST})
    public String join(DTO.JoinDTO joinDTO) {
        boolean result = joinService.joinProcess(joinDTO);
        if (result) {
            return "ok";
        } else {
            return "no";
        }
    }

    @RequestMapping(value = "/login", method = {RequestMethod.GET})
    public void requestLogin(HttpServletRequest request) {
        System.out.println("login");
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET})
    public void requestLogout(HttpServletRequest request) {

    }
}
