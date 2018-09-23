package com.weatherly.demo.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherly.demo.entities.Statistics;
import com.weatherly.demo.repositories.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StatsController {

    @Autowired
    private StatisticsRepository statisticsRepository;

    @GetMapping("/stats")
    public String homePage(Model model) throws JsonProcessingException {

      //  ObjectMapper objectMapper = new ObjectMapper();
      //  model.addAttribute("test", objectMapper.writeValueAsString("testing"));

        model.addAttribute("chromeCount", statisticsRepository.countByBrowser("Chrome"));


        return "stats";
    }



}
