package com.weatherly.demo.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherly.demo.entities.BrowserData;
import com.weatherly.demo.entities.OsData;
import com.weatherly.demo.entities.Statistics;
import com.weatherly.demo.repositories.StatisticsRepository;
import com.weatherly.demo.services.Location;
import com.weatherly.demo.services.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StatsController {

    @Autowired
    private StatisticsRepository statisticsRepository;

    @GetMapping("/stats")
    public String homePage(Model model) {





        model.addAttribute("chromeCount", statisticsRepository.countByBrowser("Chrome"));


        return "stats";
    }


    @GetMapping("/data/browser")
    @ResponseBody
    public BrowserData getBrowserData (){
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        BrowserData data = new BrowserData(statisticsRepository.countByBrowser("Chrome"),
                statisticsRepository.countByBrowser("Safari") ,
                statisticsRepository.countByBrowser("Firefox"),
                statisticsRepository.countByBrowser("Unknown"));


        return data;
    }

    @GetMapping("/data/os")
    @ResponseBody
    public OsData getOsData (){
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        OsData data = new OsData(statisticsRepository.countByOs("Windows"),
                statisticsRepository.countByOs("Mac"),
                statisticsRepository.countByOs("Unix"),
                statisticsRepository.countByOs("Android"),
                statisticsRepository.countByOs("IPhone"),
                statisticsRepository.countByOs("UnKnown"));


        return data;
    }




}