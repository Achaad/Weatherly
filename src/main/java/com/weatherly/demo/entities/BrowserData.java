package com.weatherly.demo.entities;

public class BrowserData {


    private  Integer Chrome;
    private  Integer Safari;
    private  Integer FireFox;
    private  Integer Other;

    public BrowserData(Integer Chrome, Integer Safari, Integer FireFox, Integer Other){
        this.Chrome = Chrome;
        this.Safari = Safari;
        this.FireFox = FireFox;
        this.Other = Other;
    }


    public Integer getChrome() {
        return Chrome;
    }

    public void setChrome(Integer chrome) {
        Chrome = chrome;
    }

    public Integer getSafari() {
        return Safari;
    }

    public void setSafari(Integer safari) {
        Safari = safari;
    }

    public Integer getFireFox() {
        return FireFox;
    }

    public void setFireFox(Integer fireFox) {
        FireFox = fireFox;
    }

    public Integer getOther() {
        return Other;
    }

    public void setOther(Integer other) {
        Other = other;
    }



}
