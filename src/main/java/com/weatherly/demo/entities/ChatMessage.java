package com.weatherly.demo.entities;

public class ChatMessage {

    private String content;

    //Empty Constructor required
    public ChatMessage(){

    }

    public ChatMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}