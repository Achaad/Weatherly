package com.weatherly.demo.controller;

import com.weatherly.demo.services.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Locale;

@Controller
public class UserController {

  @Autowired
  public MailSender mailSender;


  @GetMapping("/user")
  public String homePage(Model model, OAuth2Authentication authentication, HttpServletRequest servletRequest) {


    // Gets user information
    LinkedHashMap<String, String> userDetails =
        (LinkedHashMap<String, String>) authentication.getUserAuthentication().getDetails();
    String userMail = userDetails.get("email");
    String userName = userDetails.get("name");
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    String ipAddress = servletRequest.getHeader("x-real-ip"); //Returns IP Address
    String visitTime = dateFormat.format(Calendar.getInstance().getTime());
    String subject = "Login";

    MessageFormat format = new MessageFormat("Hello {0},\n" +
        "We have noticed that You have accessed our webpage on {1} from this ip: {2}. If it was " +
        "not You, please contact us at weatherly.me@gmail.com");
    format.format(userName, visitTime, ipAddress);
    mailSender.sendSimpleMessage(userMail, subject, format.toString());

    return "user";
  }
}
