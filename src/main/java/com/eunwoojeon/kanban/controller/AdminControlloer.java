package com.eunwoojeon.kanban.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminControlloer {
    @RequestMapping(value = "/admin", method = {RequestMethod.GET})
    public String adminP() {
        return "Admin Controller";
    }
}
