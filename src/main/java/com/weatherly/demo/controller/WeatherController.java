package com.weatherly.demo.controller;


import com.weatherly.demo.entities.Statistics;
import com.weatherly.demo.services.Location;
import com.weatherly.demo.services.Weather;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class WeatherController {


    // Weather API to be redirected to ... (Might have to change responsebody)
    // http://localhost:8080/weather?country=Estonia&regionName=Tartumaa&city=Tartu

    @GetMapping(path="/weather") // Map ONLY GET Requests
    @ResponseBody
    public String addNewUser (@RequestParam String country
            , @RequestParam String regionName, @RequestParam String city) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Location location = new Location(country, regionName, city);
        Weather weather = new Weather(location);

        return weather.toString();
    }

}
