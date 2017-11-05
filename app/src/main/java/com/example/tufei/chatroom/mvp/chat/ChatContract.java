package com.example.tufei.chatroom.mvp.chat;

import com.example.tufei.chatroom.base.BasePresenter;
import com.example.tufei.chatroom.base.BaseView;

/**
 * @author wzh
 * @date 2017/11/4
 */
public interface ChatContract {
    interface View extends BaseView<Presenter> {


    }

    interface Presenter extends BasePresenter<View> {

    }
}
