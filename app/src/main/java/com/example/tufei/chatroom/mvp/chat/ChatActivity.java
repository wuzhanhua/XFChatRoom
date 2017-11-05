package com.example.tufei.chatroom.mvp.chat;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tufei.chatroom.R;
import com.example.tufei.chatroom.base.BaseView;
import com.example.tufei.chatroom.bean.ChatData;
import com.iflytek.cloud.ui.RecognizerDialog;

import java.util.ArrayList;

/**
 * @author wzh
 * @date 2017/11/4
 */
public class ChatActivity extends Activity implements ChatContract.View{

    // 语音听写
    private RecognizerDialog mIatDialog;

    private ChatContract.Presenter mChatPresenter;

    private RecyclerView rlChatroom;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        rlChatroom = (RecyclerView) findViewById(R.id.rl_chatroom);
        Button btnTalk = (Button) findViewById(R.id.btn_talk);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rlChatroom.setLayoutManager(mLayoutManager);
        rlChatroom.setAdapter(mAdapter);
    }

    private void initData() {

        mChatPresenter = new ChatPresenter(this);

        mAdapter = new MyAdapter(getData());
    }

    private ArrayList<ChatData> getData() {
        ArrayList<ChatData> list=new ArrayList<ChatData>();
        for (int i = 0; i < 10; i++) {
            ChatData data=new ChatData("提问"+i,"回答"+i);
            list.add(data);
        }
        return list;
    }


    /**
     *聊天窗口recyclerview数据适配器
     */
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private ArrayList<ChatData> data;

        public MyAdapter(ArrayList<ChatData> data) {
            this.data=data;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = View.inflate(ChatActivity.this, R.layout.item_rl_chatroom, null);
            ViewHolder holder = new ViewHolder(itemView);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {

            holder.tvAsk.setText(data.get(position).getAsk());
            holder.tvAnswer.setText(data.get(position).getAnswer());

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvAsk;
            TextView tvAnswer;
            public ViewHolder(View itemView) {
                super(itemView);
                tvAsk = itemView.findViewById(R.id.tv_ask);
                tvAnswer = itemView.findViewById(R.id.tv_answer);
            }
        }
    }



}
