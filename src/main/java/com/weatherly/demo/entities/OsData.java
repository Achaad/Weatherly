package com.weatherly.demo.entities;

public class OsData {

    private Integer Windows;
    private Integer Mac;
    private Integer Linux;
    private Integer Android;
    private Integer IPhone;
    private Integer Unknown;

    public OsData(Integer windows, Integer mac, Integer linux, Integer android, Integer IPhone, Integer unknown) {
        Windows = windows;
        Mac = mac;
        Linux = linux;
        Android = android;
        this.IPhone = IPhone;
        Unknown = unknown;
    }

    public Integer getWindows() {
        return Windows;
    }

    public void setWindows(Integer windows) {
        Windows = windows;
    }

    public Integer getMac() {
        return Mac;
    }

    public void setMac(Integer mac) {
        Mac = mac;
    }

    public Integer getLinux() {
        return Linux;
    }

    public void setLinux(Integer linux) {
        Linux = linux;
    }

    public Integer getAndroid() {
        return Android;
    }

    public void setAndroid(Integer android) {
        Android = android;
    }

    public Integer getIPhone() {
        return IPhone;
    }

    public void setIPhone(Integer IPhone) {
        this.IPhone = IPhone;
    }

    public Integer getUnknown() {
        return Unknown;
    }

    public void setUnknown(Integer unknown) {
        Unknown = unknown;
    }






}
