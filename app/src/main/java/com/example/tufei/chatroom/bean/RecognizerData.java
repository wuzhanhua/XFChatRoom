package com.example.tufei.chatroom.bean;

import java.util.List;

/**
 * 语音听写数据
 */

public class RecognizerData {


    /**
     * bg : 0
     * ed : 0
     * ls : false
     * sn : 1
     * ws : [{"bg":0,"cw":[{"sc":0,"w":"讯飞"}]},{"bg":0,"cw":[{"sc":0,"w":"语音"}]}]
     */

    private List<WsBean> ws;

    public List<WsBean> getWs() {
        return ws;
    }

    public void setWs(List<WsBean> ws) {
        this.ws = ws;
    }

    public static class WsBean {
        /**
         * bg : 0
         * cw : [{"sc":0,"w":"讯飞"}]
         */

        private List<CwBean> cw;

        public List<CwBean> getCw() {
            return cw;
        }

        public void setCw(List<CwBean> cw) {
            this.cw = cw;
        }

        public static class CwBean {
            /**
             * sc : 0.0
             * w : 讯飞
             */

            private String w;

            public String getW() {
                return w;
            }

            public void setW(String w) {
                this.w = w;
            }
        }
    }
}
