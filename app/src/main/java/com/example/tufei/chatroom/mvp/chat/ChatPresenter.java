package com.example.tufei.chatroom.mvp.chat;


import android.text.TextUtils;
import android.util.Log;

import com.example.tufei.chatroom.adapter.ChatAdapter;
import com.example.tufei.chatroom.bean.ChatData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public void start() {

        //开始语音识别
        startspeechrecognize();

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

    @Override
    public void startspeechrecognize() {
        //设置语音识别监听接口,把监听对象传递给v层，由v层开始调用
        //开始语音识别
        mView.startRecognize(mModel.getRecognizeListener());
    }

    @Override
    public void startSpeechSpeak(int position) {
        ChatData data = datas.get(position);
        startSpeechSpeak(data.getText());
    }

    private void startSpeechSpeak(String text) {
        mModel.startSpeechSpeak(text);
    }

    private void startTextUnderStand(String text) {

        mModel.getUnderStandText(text, new ChatModel.UnderstandCallback() {
            @Override
            public void onUnderstanded(String understandText) {

                String text = "";
                Log.v(TAG, understandText);
                if (!TextUtils.isEmpty(understandText)) {
                    text = understandText;
                    Log.v(TAG, "语义理解结果:" + text);

                } else {

                    //生成一个随机回答
                    String randomAnswer[] = {"我无语了......", "听不懂", "静静看你装逼", "抱歉，我不知道"};
                    Random random = new Random();
                    int i = random.nextInt() * randomAnswer.length;
                    text = randomAnswer[i];
                }


                ChatData answerData = new ChatData(text, false);
                datas.add(answerData);
                mAdapter.notifyDataSetChanged();

                //开始语音合成
                startSpeechSpeak(text);
            }

        });
    }

}
