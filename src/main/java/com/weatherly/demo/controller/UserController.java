package com.weatherly.demo.controller;

import com.weatherly.demo.entities.User;
import com.weatherly.demo.entities.UserLocation;
import com.weatherly.demo.repositories.UserRepository;
import com.weatherly.demo.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class UserController {

  private static int currentPage = 1;
  private static int pageSize = 5;

  final UserService userService;

  @Autowired
  UserController(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  private MailSender mailSender;

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/user")
  public String homePage(Model model, OAuth2Authentication authentication, HttpServletRequest servletRequest,
                         @RequestParam("page") Optional<Integer> page,
                         @RequestParam("size") Optional<Integer> size) {

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

    userRepository.save(user);


    // Sends mail to the user
    String message = "Hello " + userName + ",\n" +
        "We have noticed that You have accessed our webpage on " + visitTime + " from this ip: " +
        ipAddress + ". If it was " +
        "not You, please contact us at weatherly.me@gmail.com";
    mailSender.sendSimpleMessage(userMail, subject, message);

    // Pagination
    page.ifPresent(p -> currentPage = p);
    size.ifPresent(s -> pageSize = s);

    Page<UserLocation> locationPage = userService.listLocationsById(PageRequest.of(currentPage - 1, pageSize), user.getId());

    model.addAttribute("locationPage", locationPage);

    int totalPages = locationPage.getTotalPages();
    if (totalPages > 0) {
      List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
      model.addAttribute("pageNumbers", pageNumbers);
    }

    return "user.html";
  }


  /**
   * Saves location added by the user to the database
   * @param location String. Should be in format Country,Region,City unless we implement a hashmap for locations
   * @param redirectAttributes used to redirect back to the /user page
   * @param authentication used to get the information about the logged in user
   * @return redirects back to the user page
   */
  @PostMapping(value = "/user/submitLocation")
  public String uploadLocation(@RequestParam("userLocation") String location, RedirectAttributes redirectAttributes, OAuth2Authentication authentication) {
    // Gets details of the user
    LinkedHashMap<String, String> userDetails =
            (LinkedHashMap<String, String>) authentication.getUserAuthentication().getDetails();
    User user = userRepository.findByUserId(userDetails.get("id")); // Gets user based on its userId

    // We check if the string is in the right format (wether it consists of three elements separated by commas)
    if (location.split(",").length != 3) {
      redirectAttributes.addFlashAttribute("message", "Location is in wrong format.");
      return "redirect:/user";
    }

    // Check if the location is existant and findable at yr.no
    UserLocation userLocation = new UserLocation(location);
    Location loc = new Location(userLocation.getCountry(), userLocation.getRegion(), userLocation.getCity());
    try {
      if (new Weather(loc).getWeatherState().equals("tundmatu")) {
        redirectAttributes.addFlashAttribute("message", "Location is non-existant. Check it at https://yr.no");
        return "redirect:/user";
      }
    } catch (NullPointerException e) {
      redirectAttributes.addFlashAttribute("message", "Location is non-existant. Check it at https://yr.no");
      return "redirect:/user";
    }

    // If it reaches here, then everything is correct
    user.addLoaction(userLocation);

    User newUser = userRepository.save(user);
    /* TODO: maybe remove, not sure if these check are necessary...
    if (user.equals(newUser)) {
      //return ResponseEntity.status(HttpStatus.OK).build();
    } else {
      //return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }*/
    redirectAttributes.addFlashAttribute("message", "Location submitted successfully.");
    return "redirect:/user";
  }
}
