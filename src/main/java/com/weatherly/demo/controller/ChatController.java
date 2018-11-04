package com.weatherly.demo.controller;

import com.weatherly.demo.entities.Greeting;
import com.weatherly.demo.entities.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.HtmlUtils;

@Controller
public class ChatController {


    @MessageMapping("/message")
    @SendTo("/topic/webchat")
    public Greeting greeting(ChatMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Message: " + HtmlUtils.htmlEscape(message.getContent()));
    }

    @GetMapping(value = "/webchat")
    public String homePage() {


        return "chat";
    }

}