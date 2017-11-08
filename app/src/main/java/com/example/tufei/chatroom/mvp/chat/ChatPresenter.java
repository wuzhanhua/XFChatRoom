package com.example.tufei.chatroom.mvp.chat;


import android.text.TextUtils;
import android.util.Log;

import com.example.tufei.chatroom.adapter.ChatAdapter;
import com.example.tufei.chatroom.bean.ChatData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wzh
 * @date 2017/11/4
 */
public class ChatPresenter implements ChatContract.Presenter {

    private static final String TAG = "ChatPresenter";
    private ChatContract.View mView;
    private ChatModel mModel;

    private String recognizeText;
    private List<ChatData> datas;
    private ChatAdapter mAdapter;


    public ChatPresenter(ChatContract.View view, ChatModel model) {
        mView = view;
        mModel = model;

        init();

    }

    private void init() {
        datas = new ArrayList<ChatData>();
        mAdapter = new ChatAdapter((ChatActivity) mView, datas);
        mView.setAdapter(mAdapter);


    }


    @Override
    public void attachView(ChatContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }


    @Override
    public void startspeechrecognize() {

        //设置语音识别监听接口
        mView.setRecognizerDialogListener(mModel.getRecognizeListener());

        //开始语音识别
        mView.startRecognize();

        //获取识别结果
        mModel.getRecognizeText(new ChatModel.RecognizeCallback() {
            @Override
            public void onRecognized(String recognizeText) {

                if (!TextUtils.isEmpty(recognizeText)) {
                    Log.v(TAG, "语音识别结果:" + recognizeText);
                    ChatData askData = new ChatData(recognizeText, true);
                    datas.add(askData);
                    mAdapter.notifyDataSetChanged();

                    //开始语义理解
                    startTextUnderStand(recognizeText);
                }

            }
        });
    }

    private void startSpeechSpark(String text) {
        mModel.startSpeechSpeak(text);
    }

    private void startTextUnderStand(String text) {

        mModel.getUnderStandText(text, new ChatModel.UnderstandCallback() {
            @Override
            public void onUnderstanded(String understandText) {
                if (!TextUtils.isEmpty(understandText)) {
                    Log.v(TAG, "语义理解结果:" + understandText);
                    ChatData answerData = new ChatData(understandText, false);
                    datas.add(answerData);
                    mAdapter.notifyDataSetChanged();

                    //开始语音合成
                    startSpeechSpark(understandText);
                }
            }

        });
    }

}
