package com.weatherly.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IdLoginController {

    @GetMapping("/idlogin")
    public String homePage(Model model){

        return "idlogin";
    }


}
