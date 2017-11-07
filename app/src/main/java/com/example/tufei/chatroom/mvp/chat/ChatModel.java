package com.example.tufei.chatroom.mvp.chat;


import android.text.TextUtils;

import com.example.tufei.chatroom.bean.RecognizerData;
import com.example.tufei.chatroom.bean.UnderStanderData;
import com.example.tufei.chatroom.utils.ToastUtil;
import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
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

    private static ChatModel INSTANCE;

    public static ChatModel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChatModel();
        }
        return INSTANCE;
    }

    public void getRecognizeText(RecognizeCallback callback) {
        mRecognizeCallback=callback;

    }

    public void getUnderStandText(UnderstandCallback callback) {
        mUnderstandCallback=callback;
    }


    public RecognizerDialogListener getRecognizeListener() {
        return mRecognizerDialogListener;
    }

    public TextUnderstanderListener getTextUnderstanderListener() {
        return mTextUnderstanderListener;
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

                    parseResult(recognizerResult.getResultString());
                    //解析完成，调用回调方法
                    mRecognizeCallback.onRecognized(recognizeText);
                }else{
                    recognizeText="";
                }
            }
        }

        @Override
        public void onError(SpeechError speechError) {
            recognizeText=null;
        }
    };

    private void parseResult(String json) {
        String speechText;
        StringBuffer str = new StringBuffer();
        Gson gson = new Gson();
        RecognizerData data = gson.fromJson(json, RecognizerData.class);
        List<RecognizerData.WsBean> ws = data.getWs();
        for (RecognizerData.WsBean w : ws) {
            str.append(w.getCw().get(0).getW());
        }

        recognizeText = str.toString();

    }




    /**
     * 文本语义监听
     */
    public static final String errorTip = "请确认是否有在 aiui.xfyun.cn 配置语义。（另外，已开通语义，但从1115（含1115）以前的SDK更新到1116以上版本SDK后，语义需要重新到 aiui.xfyun.cn 配置）";
    private TextUnderstanderListener mTextUnderstanderListener = new TextUnderstanderListener() {

        @Override
        public void onResult(final UnderstanderResult result) {
            if (null != result) {
                parseUnderStanderResult(result.getResultString());
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

    private void parseUnderStanderResult(String json) {

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

}
