package com.juguo.magazine.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.juguo.magazine.bean.PieceBean;
import com.juguo.magazine.bean.ReadHistoryBean;
import com.juguo.magazine.remote.HotRecordHolder;
import com.juguo.magazine.remote.ReadRecordHolder;

import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.RecyclerAdapter;


public class ReadRecordAdapter extends RecyclerAdapter<ReadHistoryBean.ReadHistory> {

    private OnItemClickListener onItemClickListener;
    public ReadRecordAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder<ReadHistoryBean.ReadHistory> onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ReadRecordHolder(parent);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<ReadHistoryBean.ReadHistory> holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(holder.getData());
            }
            Log.e("TAG","ddddd" +holder.getData());

        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(ReadHistoryBean.ReadHistory data);
    }
}