package com.weatherly.demo.controller;


import com.weatherly.demo.entities.Statistics;
import com.weatherly.demo.services.Location;
import com.weatherly.demo.services.Weather;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@Controller
public class WeatherController {


    // Weather API to be redirected to ... (Might have to change responsebody)
    // http://localhost:8080/data/weather?country=Estonia&regionName=Tartumaa&city=Tartu

    @GetMapping(path="/data/weather") // Map ONLY GET Requests
    public String weatherPage (Model model, @RequestParam String country
            , @RequestParam String regionName, @RequestParam String city) {

        Location location = new Location(country, regionName, city);
        Weather weather = new Weather(location);


        String asukoht = location.getCity() + ", " + location.getCountry() +
                ", " + location.getRegionName();


        String background = "clear";


        if(weather.getWeatherState().toLowerCase().contains("cloud"))
            background = "cloudy";
        if(weather.getWeatherState().toLowerCase().contains("rain") || weather.getWeatherState().toLowerCase().contains("shower"))
            background = "rainy";
        if(weather.getWeatherState().toLowerCase().contains("snow"))
            background = "snowy";


        model.addAttribute("asukoht", asukoht);
        model.addAttribute("taust", background);
        model.addAttribute("temperatuur", weather.getTemp());
        model.addAttribute("sademed", weather.getPrecipitation());
        model.addAttribute("suund", weather.getWindDirection());
        model.addAttribute("kiirus", weather.getWindSpeed());
        model.addAttribute("ilmaStaatus", weather.getWeatherState());

        return "weather";
    }

}