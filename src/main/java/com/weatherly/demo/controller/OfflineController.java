package com.weatherly.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OfflineController {

    @GetMapping("/offline")
    public String homePage(Model model) {
        return "offline";
    }
}
