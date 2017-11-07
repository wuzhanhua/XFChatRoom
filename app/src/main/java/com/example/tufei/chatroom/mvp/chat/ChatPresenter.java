package com.example.tufei.chatroom.mvp.chat;


import android.text.TextUtils;
import android.util.Log;

import com.example.tufei.chatroom.adapter.ChatAdapter;
import com.example.tufei.chatroom.bean.ChatData;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.TextUnderstander;

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

    // 语音合成对象
    private SpeechSynthesizer mTts;

    // 语义理解对象（文本到语义）。
    private TextUnderstander  mTextUnderstander;





    public ChatPresenter(ChatContract.View view, ChatModel model) {
        mView = view;
        mModel = model;

        init();

    }

    private void init() {
        datas = new ArrayList<ChatData>();
        mAdapter = new ChatAdapter((ChatActivity) mView, datas);
        mView.setAdapter(mAdapter);

        // 初始化语音合成对象
        mTts = SpeechSynthesizer.createSynthesizer((ChatActivity) mView, null);

        //初始化语义理解对象
        mTextUnderstander = TextUnderstander.createTextUnderstander((ChatActivity) mView,null );
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

        //设置监听接口
        mView.setRecognizerDialogListener(mModel.getRecognizeListener());
        //开始语音识别
        mView.startRecognize();
        //获取识别结果
        mModel.getRecognizeText(new ChatModel.RecognizeCallback() {
            @Override
            public void onRecognized(String resultText) {
                //通知界面更新
                if (!TextUtils.isEmpty(recognizeText)) {
                    Log.v(TAG, "语音识别结果:" + recognizeText);
                    ChatData askData = new ChatData(recognizeText, true);
                    datas.add(askData);
                    mAdapter.notifyDataSetChanged();

                    //开始语义理解
                    startTextUnderStand(recognizeText);

                    //获取理解结果
                    mModel.getUnderStandText(new ChatModel.UnderstandCallback() {
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
        });







    }

    private void startSpeechSpark(String text) {
    }

    private void startTextUnderStand(String text) {
        int ret = 0;// 函数调用返回值
        if (TextUtils.isEmpty(text)) {
            text="我听不见听不见听不见，亲请再说一遍";
        }else{
            if(mTextUnderstander.isUnderstanding()){
                mTextUnderstander.cancel();
                mView.showToast("取消");
            }else {
                // 设置语义情景
                mTextUnderstander.setParameter(SpeechConstant.SCENE, "main");
                //开始
                ret = mTextUnderstander.understandText(text, mModel.getTextUnderstanderListener());
                if(ret != 0)
                {
                    mView.showToast("语义理解失败,错误码:"+ ret);
                }
            }
        }
    }

}
