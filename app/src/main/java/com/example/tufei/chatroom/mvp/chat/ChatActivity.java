package com.example.tufei.chatroom.mvp.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.tufei.chatroom.R;
import com.example.tufei.chatroom.adapter.ChatAdapter;
import com.example.tufei.chatroom.base.BaseActivity;
import com.example.tufei.chatroom.di.Injection;
import com.example.tufei.chatroom.utils.ToastUtil;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

/**
 * @author wzh
 * @date 2017/11/4
 */
public class ChatActivity extends BaseActivity implements ChatContract.View, View.OnClickListener {

    private ChatContract.Presenter mChatPresenter;

    private RecyclerView rlChatroom;

    // 语音听写
    private RecognizerDialog mIatDialog;
    private RecognizerDialogListener mRecognizerDialogListener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initView();
        initData();
    }

    private void initView() {
        rlChatroom = findViewById(R.id.rl_chatroom);
        Button btnTalk = findViewById(R.id.btn_talk);
        btnTalk.setOnClickListener(this);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlChatroom.setLayoutManager(mLayoutManager);


    }


    private void initData() {

        mChatPresenter = new ChatPresenter(this, Injection.provideChatModel());

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(this, null);

    }


//    private ArrayList<ChatData> getData() {
//        ArrayList<ChatData> list = new ArrayList<ChatData>();
//        for (int i = 0; i < 10; i++) {
//            ChatData data = new ChatData("提问" + i, "回答" + i);
//            list.add(data);
//        }
//        return list;
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //语音识别
            case R.id.btn_talk:

                Log.v("ChatPresenter","语音识别开始");
                //开始语音识别
                mChatPresenter.start();

                break;

            default:
                break;
        }
    }

    @Override
    public void showToast(String text) {
        ToastUtil.showToast(text);
    }

    @Override
    public void startRecognize() {
        mIatDialog.show();
    }

    @Override
    public void setAdapter(ChatAdapter adapter) {
        rlChatroom.setAdapter(adapter);
    }

    @Override
    public void setRecognizerDialogListener(RecognizerDialogListener listener) {
        mIatDialog.setListener(listener);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mChatPresenter.detachView();
    }
}
