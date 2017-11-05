package com.example.tufei.chatroom.mvp.chat;


/**
 * @author wzh
 * @date 2017/11/4
 */
public class ChatPresenter implements ChatContract.Presenter {

    private ChatContract.View mView;



    public ChatPresenter(ChatContract.View view) {
        mView = view;
    }


    @Override
    public void attachView(ChatContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }


    public String speechRecognize() {
        return null;

    }
}
