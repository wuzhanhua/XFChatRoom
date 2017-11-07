package com.example.tufei.chatroom.di;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.tufei.chatroom.mvp.chat.ChatModel;

/**
 * @author wzh
 * @date 2017/11/5
 */
public class Injection {

    public static ChatModel provideChatModel() {
        return ChatModel.getInstance();
    }
}
