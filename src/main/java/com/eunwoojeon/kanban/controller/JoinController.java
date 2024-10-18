package com.eunwoojeon.kanban.controller;

import com.eunwoojeon.kanban.dto.DTO.*;
import com.eunwoojeon.kanban.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/kanban/api")
public class JoinController {

    @Autowired
    private JoinService joinService;

    @RequestMapping(value = "/join", method = {RequestMethod.POST})
    public String joinProcess(JoinDTO joinDTO) {
        boolean result = joinService.joinProcess(joinDTO);
        if (result) {
            return "ok";
        } else {
            return "no";
        }
    }
}
