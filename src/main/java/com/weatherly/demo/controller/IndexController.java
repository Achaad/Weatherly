package com.weatherly.demo.controller;


import com.weatherly.demo.Database.Statistics;
import com.weatherly.demo.Database.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Controller
public class IndexController {

    @Value("${spring.application.name}")
    String appName;

    @Autowired
    private StatisticsRepository statisticsRepository;


    private String os = "";
    private String visitTime = "";
    private String ipAddress = "";
    private String browser = "";
    private String location = "";


    @GetMapping("/")
    public String homePage(Model model, HttpServletRequest servletRequest) {

        model.addAttribute("appName", appName);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale
            .ENGLISH);

        this.ipAddress = servletRequest.getRemoteAddr(); //Returns IP Address
        parseHeaderAgent(servletRequest.getHeader("User-Agent"));
        this.visitTime = dateFormat.format(Calendar.getInstance().getTime());

        Statistics s = new Statistics();
        s.setIp(ipAddress);
        try {

            s.setDate(dateFormat.parse(this.visitTime));
        } catch (ParseException e) {
            s.setDate(Calendar.getInstance().getTime());
        }
        s.setOs(this.os);

        return "index";    //vajalik pannab HTML faili nimi
    }

    @GetMapping("/stats")
    public @ResponseBody Iterable<Statistics> getAllStats() {
        // This returns a JSON or XML with the statistics
        return statisticsRepository.findAll();
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
