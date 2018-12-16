package com.weatherly.demo.controller;


import com.weatherly.demo.entities.Statistics;
import com.weatherly.demo.repositories.StatisticsRepository;
import com.weatherly.demo.services.Location;
import com.weatherly.demo.services.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Null;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

@Controller
public class IndexController {

    @Autowired
    private StatisticsRepository statisticsRepository;


    private String os = "";
    private String visitTime = "";
    private String ipAddress = "";
    private String browser = "";
    private Location location;
    private Boolean mobile;



    @GetMapping(value = {"/","/index"})
    public String homePage(Model model, HttpServletRequest servletRequest) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

        this.ipAddress = servletRequest.getHeader("x-real-ip"); //Returns IP Address
        this.ipAddress = "193.40.12.10"; //TODO: Revert back for deployment
      
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
        s.setBrowser(this.browser);
        s.setMobile(this.mobile);





        //Get LAT/LONG based on IP.

            location = new Location(this.ipAddress);
            Weather weather = new Weather(location.getLatitude(), location.getLongitude());
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
            model.addAttribute("location", location.toString());
            model.addAttribute("weather", weather.toString());
            model.addAttribute("ilmaStaatus", weather.getWeatherState());
            statisticsRepository.save(s);

        return "index";    //  vajalik panna HTML faili nimi
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

        // Browseri parse tugi
        // User-Agent on kirjeldatud veebilehel: https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/User-Agent
        if (lower.contains("firefox")) {
            this.browser = "Firefox";
        } else if (lower.contains("chrome") && !lower.contains("opr")) {
            this.browser = "Chrome";
        } else if (lower.contains("opr")) {
            this.browser = "Safari";
        } else if (lower.contains("safari") && !lower.contains("chrome")) {
            this.browser = "Safari";
        } else {
            this.browser = "Unknown";
        }

        // Parsib, kas on tegemist mobiilse browseriga või mitte
        if (lower.contains("mobile")) {
            this.mobile = true;
        } else {
            this.mobile = false;
        }

    }
}
