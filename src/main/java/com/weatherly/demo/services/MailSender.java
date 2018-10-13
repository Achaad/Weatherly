package com.weatherly.demo.services;

public interface MailSender {
  public void sendSimpleMessage(String to, String subject, String  text);
}
