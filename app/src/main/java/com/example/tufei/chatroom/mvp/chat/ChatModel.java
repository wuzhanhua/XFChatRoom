package com.example.tufei.chatroom.mvp.chat;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.example.tufei.chatroom.App;
import com.example.tufei.chatroom.bean.RecognizerData;
import com.example.tufei.chatroom.bean.UnderStanderData;
import com.example.tufei.chatroom.utils.ToastUtil;
import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.TextUnderstander;
import com.iflytek.cloud.TextUnderstanderListener;
import com.iflytek.cloud.UnderstanderResult;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import java.util.List;

/**
 * @author wzh
 * @date 2017/11/5
 */
public class ChatModel {

    private String recognizeText;
    private String understandText;

    private RecognizeCallback mRecognizeCallback;
    private UnderstandCallback mUnderstandCallback;

    // 语音合成对象
    private SpeechSynthesizer mTts;

    // 语义理解对象（文本到语义）。
    private TextUnderstander  mTextUnderstander;

    private static ChatModel INSTANCE;

    public ChatModel() {
        // 初始化语音合成对象
        mTts = SpeechSynthesizer.createSynthesizer(App.getApplication(), null);

        //初始化语义理解对象
        mTextUnderstander = TextUnderstander.createTextUnderstander(App.getApplication(),null );
    }

    public static ChatModel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChatModel();
        }

        return INSTANCE;
    }

    public void getRecognizeText(RecognizeCallback callback) {
        mRecognizeCallback=callback;

    }

    public void getUnderStandText(String text,UnderstandCallback callback) {
        mUnderstandCallback=callback;

        int ret = 0;// 函数调用返回值
        if (TextUtils.isEmpty(text)) {
            text="我听不见听不见听不见，请再说一遍";
        }else{
            if(mTextUnderstander.isUnderstanding()){
                mTextUnderstander.cancel();
//                mView.showToast("取消");
            }else {
                // 设置语义情景
                mTextUnderstander.setParameter(SpeechConstant.SCENE, "main");
                //开始
                ret = mTextUnderstander.understandText(text, mTextUnderstanderListener);
                if(ret != 0)
                {
//                    mView.showToast("语义理解失败,错误码:"+ ret);
                }
            }
        }
    }

    public void startSpeechSpeak(String text) {
        mTts.startSpeaking(text,mTtsListener);


    }


    public RecognizerDialogListener getRecognizeListener() {
        return mRecognizerDialogListener;
    }




    /**
     * 语音听写回调
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean isLast) {
            recognizeText = "";
            if (!isLast) {
                if (!TextUtils.isEmpty(recognizerResult.getResultString())) {

                    String result = parseRecognizeResult(recognizerResult.getResultString());
                    //解析完成，调用回调方法
                    mRecognizeCallback.onRecognized(result);
                }
            }
        }

        @Override
        public void onError(SpeechError speechError) {
            recognizeText=null;
        }
    };

    private String parseRecognizeResult(String json) {
        String speechText;
        StringBuffer str = new StringBuffer();
        Gson gson = new Gson();
        RecognizerData data = gson.fromJson(json, RecognizerData.class);
        List<RecognizerData.WsBean> ws = data.getWs();
        for (RecognizerData.WsBean w : ws) {
            str.append(w.getCw().get(0).getW());
        }

        recognizeText = str.toString();

        Log.v("ChatPresenter", "识别结果：" + recognizeText);
        return recognizeText;

    }




    /**
     * 文本语义监听
     */
    public static final String errorTip = "请确认是否有在 aiui.xfyun.cn 配置语义。（另外，已开通语义，但从1115（含1115）以前的SDK更新到1116以上版本SDK后，语义需要重新到 aiui.xfyun.cn 配置）";
    private TextUnderstanderListener mTextUnderstanderListener = new TextUnderstanderListener() {

        @Override
        public void onResult(final UnderstanderResult result) {
            if (null != result) {
                parseUnderstanderResult(result.getResultString());
                mUnderstandCallback.onUnderstanded(understandText);
            }
        }

        @Override
        public void onError(SpeechError speechError) {
            // 文本语义不能使用回调错误码14002，请确认您下载sdk时是否勾选语义场景和私有语义的发布
            // 请到 aiui.xfyun.cn 配置语义，从1115前的SDK更新到1116以上版本SDK后，语义需要重新到 aiui.xfyun.cn 配置
            ToastUtil.showToast("onError Code：" + speechError.getErrorCode() + ", " + errorTip);
        }

    };

    private void parseUnderstanderResult(String json) {

        if (!TextUtils.isEmpty(json)) {
            Gson gson = new Gson();
            UnderStanderData data = gson.fromJson(json, UnderStanderData.class);
            understandText=data.getAnswer().getText();
        }
    }

    interface RecognizeCallback {
        void onRecognized(String resultText);
    }

    interface UnderstandCallback{
        void onUnderstanded(String resultText);
    }

    /**
     * 语音合成监听
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {


        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPo) {

        }

        @Override
        public void onCompleted(SpeechError speechError) {
            if (speechError == null) {
                ToastUtil.showToast("播放完成");
            } else if (speechError != null) {
                ToastUtil.showToast(speechError.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {

        }
    };

}
