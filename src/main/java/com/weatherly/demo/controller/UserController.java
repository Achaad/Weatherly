package com.weatherly.demo.controller;

import com.weatherly.demo.entities.User;
import com.weatherly.demo.repositories.UserRepository;
import com.weatherly.demo.services.MailSender;
import com.weatherly.demo.services.MailSenderImpl;
import com.weatherly.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

@Controller
public class UserController {

  final UserService userService;

  @Autowired
  UserController(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  private MailSender mailSender;

  @Autowired
  private UserRepository userRepository;


  @RequestMapping(value = "/users", method = RequestMethod.GET)
  Page<User> list(Pageable pageable) {
    Page<User> users = userService.listAllByPage(pageable);
    return users;
  }

  @GetMapping("/user")
  public String homePage(Model model, OAuth2Authentication authentication, HttpServletRequest servletRequest) {

    List<String> userLocations;

    // Gets user information
    LinkedHashMap<String, String> userDetails =
        (LinkedHashMap<String, String>) authentication.getUserAuthentication().getDetails();
    String userMail = userDetails.get("email");
    String userName = userDetails.get("name");
    String userId = userDetails.get("id");
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    String ipAddress = servletRequest.getHeader("x-real-ip"); //Returns IP Address
    String visitTime = dateFormat.format(Calendar.getInstance().getTime());
    String subject = "Login";

    User user = new User();

    // Adds user to the table if he is not present there
    if (userRepository.findAllByUserId(userId).size() == 0) {
      user.setUserId(userId);
      user.setFirstName(userDetails.get("given_name"));
      user.setLastName(userDetails.get("family_name"));
      user.setFullName(userName);
      user.setMail(userMail);
    } else {
      user = userRepository.findByUserId(userId);
    }

    //userLocations = user.getLocations();
    userRepository.save(user);


    String message = "Hello " + userName + ",\n" +
        "We have noticed that You have accessed our webpage on " + visitTime + " from this ip: " +
        ipAddress + ". If it was " +
        "not You, please contact us at weatherly.me@gmail.com";
    mailSender.sendSimpleMessage(userMail, subject, message);



    return "user";
  }
}
