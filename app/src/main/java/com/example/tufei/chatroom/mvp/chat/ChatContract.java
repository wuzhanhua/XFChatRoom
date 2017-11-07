package com.example.tufei.chatroom.mvp.chat;

import android.support.v7.widget.RecyclerView;

import com.example.tufei.chatroom.adapter.ChatAdapter;
import com.example.tufei.chatroom.base.BasePresenter;
import com.example.tufei.chatroom.base.BaseView;
import com.iflytek.cloud.ui.RecognizerDialogListener;

/**
 * @author wzh
 * @date 2017/11/4
 */
public interface ChatContract {
    interface View extends BaseView<Presenter> {

        void startRecognize();
        void setAdapter(ChatAdapter adapter);
        void setRecognizerDialogListener(RecognizerDialogListener listener);

    }

    interface Presenter extends BasePresenter<View> {


        void startspeechrecognize();
    }
}
