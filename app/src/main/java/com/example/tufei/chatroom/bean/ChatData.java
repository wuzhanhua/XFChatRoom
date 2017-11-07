package com.example.tufei.chatroom.bean;

/**
 * Created by tufei on 2017/11/4.
 */

public class ChatData {
    private String text;

    public ChatData(String text, boolean isAsk) {
        this.text = text;
        this.isAsk = isAsk;
    }

    private boolean isAsk;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isAsk() {
        return isAsk;
    }

    public void setAsk(boolean ask) {
        isAsk = ask;
    }

    @Override
    public String toString() {
        return "ChatData{" +
                "text='" + text + '\'' +
                '}';
    }
}
