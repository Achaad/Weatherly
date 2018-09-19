package com.weatherly.demo.controller;


import com.weatherly.demo.entities.Statistics;
import com.weatherly.demo.services.Location;
import com.weatherly.demo.services.Weather;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class WeatherController {

    @GetMapping("/tartuweather")
    public @ResponseBody Weather getWeather( ) {
        // This returns a JSON or XML with the statistics
        Location location = new Location("Estonia", "Tartumaa", "Tartu");

        return new Weather(location);
    }

}
