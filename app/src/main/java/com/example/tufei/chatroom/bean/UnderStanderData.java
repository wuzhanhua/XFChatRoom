package com.example.tufei.chatroom.bean;

/**
 * 语义理解数据
 */

public class UnderStanderData {


    /**
     * answer : {"text":"请欣赏鼹鼠和雪人."}
     * dialog_stat : dataInvalid
     * rc : 0
     * save_history : true
     * semantic : [{"intent":"RANDOM_QUERY","slots":[]}]
     * service : story
     * sid : sch38ff9700@gz3c3e0d57c4f43c3e00
     * state : {"fg::story::default::default":{"state":"default"}}
     * text : 讲一个故事呗
     * used_state : {"state":"default","state_key":"fg::story::default::default"}
     * uuid : atn000128e7@chbb770d57c4f46f2a01
     */

    private AnswerBean answer;
    private int rc;

    public static class AnswerBean {
        /**
         * text : 请欣赏鼹鼠和雪人.
         */

        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public AnswerBean getAnswer() {
        return answer;
    }

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }
}
