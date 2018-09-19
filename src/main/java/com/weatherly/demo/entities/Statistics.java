package com.weatherly.demo.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 *  Entity class that represents Statistics table
 */
@Entity
public class Statistics {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String ip;

  private String os;

  private Date date;

  private String browser;

  private Boolean mobile;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getOs() {
    return os;
  }

  public void setOs(String os) {
    this.os = os;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getBrowser() {
    return browser;
  }

  public void setBrowser(String browser) {
    this.browser = browser;
  }

  public Boolean getMobile() {
    return mobile;
  }

  public void setMobile(Boolean mobile) {
    this.mobile = mobile;
  }
}
