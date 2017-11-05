package com.example.tufei.chatroom.base;

/**
 * @author wzh
 * @date 2017/11/4
 */
public interface BasePresenter<T extends BaseView> {
    void attachView(T view);
    void detachView();
}
