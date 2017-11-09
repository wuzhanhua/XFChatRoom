package com.example.tufei.chatroom.mvp.chat;

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

        void startRecognize(RecognizerDialogListener listener);
        void setAdapter(ChatAdapter adapter);

    }

    interface Presenter extends BasePresenter<View> {


        void startspeechrecognize();

        /**
         * 语音合成item位置的文本
         * @param position item位置
         */
        void startSpeechSpeak(int position);
    }
}
