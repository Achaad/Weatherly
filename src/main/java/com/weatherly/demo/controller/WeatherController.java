package com.weatherly.demo.controller;


import com.weatherly.demo.entities.Statistics;
import com.weatherly.demo.services.Location;
import com.weatherly.demo.services.Weather;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WeatherController {


    // Weather API to be redirected to ... (Might have to change responsebody)
    // http://localhost:8080/weather?country=Estonia&regionName=Tartumaa&city=Tartu

    @GetMapping(path="/data/weather") // Map ONLY GET Requests
    public String weatherPage (Model model, @RequestParam String country
            , @RequestParam String regionName, @RequestParam String city) {

        Location location = new Location(country, regionName, city);
        Weather weather = new Weather(location);
        model.addAttribute("weather", weather);

        return "weather";
    }

}
