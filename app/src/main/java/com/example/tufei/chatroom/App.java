package com.example.tufei.chatroom;

import android.app.Application;
import android.content.Context;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

/**
 * @author wzh
 * @date 2017/11/4
 */
public class App extends Application {

    private static App mApp;

    @Override
    public void onCreate() {
        super.onCreate();

        //讯飞初始化
        // 请勿在“=”与appid之间添加任何空字符或者转义符
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=59f44605");

        mApp = this;
    }

    public static Context getApplication() {
        return mApp;
    }
}
