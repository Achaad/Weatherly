package com.weatherly.demo.entities;

public class BrowserData {


    private  int Chrome;
    private  int Safari;
    private  int FireFox;
    private  int Other;

    public BrowserData(int Chrome, int Safari, int FireFox, int Other){
        this.Chrome = Chrome;
        this.Safari = Safari;
        this.FireFox = FireFox;
        this.Other = Other;
    }

    public int getChrome() {
        return Chrome;
    }

    public void setChrome(int chrome) {
        Chrome = chrome;
    }

    public int getSafari() {
        return Safari;
    }

    public void setSafari(int safari) {
        Safari = safari;
    }

    public int getFireFox() {
        return FireFox;
    }

    public void setFireFox(int fireFox) {
        FireFox = fireFox;
    }

    public int getOther() {
        return Other;
    }

    public void setOther(int other) {
        Other = other;
    }
}
