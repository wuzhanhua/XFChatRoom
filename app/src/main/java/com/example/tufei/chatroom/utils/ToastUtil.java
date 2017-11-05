package com.example.tufei.chatroom.utils;

import android.widget.Toast;

import com.example.tufei.chatroom.App;

/**
 * @author wzh
 * @date 2017/11/4
 */
public class ToastUtil {
    static Toast mToast;

    public static void showToast(String text) {
        if (mToast == null) {
            Toast.makeText(App.getApplication(), text, Toast.LENGTH_SHORT);
        }else{
            mToast.setText(text);
        }
        mToast.show();
    }
}
