package com.example.tufei.chatroom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tufei.chatroom.R;
import com.example.tufei.chatroom.bean.ChatData;

import java.util.List;

/**
 * @author wzh
 * @date 2017/11/6
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<ChatData> datas;
    private Context context;

    public ChatAdapter(Context context, List<ChatData> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_rl_chatroom, null);
        ChatViewHolder holder = new ChatViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        ChatData data = datas.get(position);
        if (data.isAsk()) {
            holder.tvAsk.setText(data.getText());
            holder.tvAsk.setVisibility(View.VISIBLE);
            holder.tvAnswer.setVisibility(View.GONE);
        } else {
            holder.tvAnswer.setText(data.getText());
            holder.tvAsk.setVisibility(View.GONE);
            holder.tvAnswer.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }


    class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView tvAsk;
        TextView tvAnswer;

        public ChatViewHolder(View itemView) {
            super(itemView);
            tvAsk = itemView.findViewById(R.id.tv_ask);
            tvAnswer = itemView.findViewById(R.id.tv_answer);
        }
    }

}
