package com.example.tufei.chatroom.bean;

/**
 * Created by tufei on 2017/11/4.
 */

public class ChatData {
    private String ask;
    private String answer;

    public ChatData(String ask, String answer) {
        this.ask = ask;
        this.answer = answer;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
