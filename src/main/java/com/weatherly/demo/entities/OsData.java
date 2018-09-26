package com.weatherly.demo.entities;

public class OsData {

    private int Windows;
    private int Mac;
    private int Linux;
    private int Android;
    private int IPhone;
    private int Unknown;

    public OsData(int windows, int mac, int linux, int android, int IPhone, int unknown) {
        Windows = windows;
        Mac = mac;
        Linux = linux;
        Android = android;
        this.IPhone = IPhone;
        Unknown = unknown;
    }


    public int getWindows() {
        return Windows;
    }

    public void setWindows(int windows) {
        Windows = windows;
    }

    public int getMac() {
        return Mac;
    }

    public void setMac(int mac) {
        Mac = mac;
    }

    public int getLinux() {
        return Linux;
    }

    public void setLinux(int linux) {
        Linux = linux;
    }

    public int getAndroid() {
        return Android;
    }

    public void setAndroid(int android) {
        Android = android;
    }

    public int getIPhone() {
        return IPhone;
    }

    public void setIPhone(int IPhone) {
        this.IPhone = IPhone;
    }

    public int getUnknown() {
        return Unknown;
    }

    public void setUnknown(int unknown) {
        Unknown = unknown;
    }
}
