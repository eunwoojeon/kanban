package com.eunwoojeon.kanban.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminControlloer {
    @GetMapping("/admin")
    public String adminP() {
        return "Admin Controller";
    }
}
