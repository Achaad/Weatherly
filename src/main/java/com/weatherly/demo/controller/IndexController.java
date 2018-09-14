package com.weatherly.demo.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
public class IndexController {

    @Value("${spring.application.name}")
    String appName;


    private String os = "";
    private String visitTime = "";
    private String ipAddress = "";
    private String browser = "";
    private String location = "";


    @GetMapping("/")
    public String homePage(Model model, HttpServletRequest servletRequest) {

        model.addAttribute("appName", appName);

        this.ipAddress = servletRequest.getRemoteAddr(); //Returns IP Address
        parseHeaderAgent(servletRequest.getHeader("User-Agent"));
        this.visitTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

        return "index";    //vajalik pannab HTML faili nimi
    }


    //Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36

    //Method that parses the user agent for the operating system type
    private void parseHeaderAgent(String headerAgent){

        String lower = headerAgent.toLowerCase();

        if (lower.contains("windows"))
        {
            this.os = "Windows";
        } else if(lower.contains("mac"))
        {
            this.os = "Mac";
        } else if(lower.contains("x11"))
        {
            this.os = "Unix";
        } else if(lower.contains("android"))
        {
            this.os = "Android";
        } else if(lower.contains("iphone"))
        {
            this.os = "IPhone";
        }else{
            this.os = "UnKnown";
        }

        //Lisada Browser parse tugi, kui viitsime, iseenesest 3 statistikat olemas.
        //Browseri korrektne parsimine ilma library kasutamata tehtav ilusti, aga hea huumor.

    }
}
