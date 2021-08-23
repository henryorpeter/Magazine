package com.juguo.magazine.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.juguo.magazine.bean.PieceBean;
import com.juguo.magazine.remote.MoreNewRecordHolder;
import com.juguo.magazine.remote.NewRecordHolder;

import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.RecyclerAdapter;


public class MoreNewRecordAdapter extends RecyclerAdapter<PieceBean.Price> {

    private OnItemClickListener onItemClickListener;
    public MoreNewRecordAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder<PieceBean.Price> onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new MoreNewRecordHolder(parent);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<PieceBean.Price> holder, int position) {
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
        void onItemClick(PieceBean.Price data);
    }
}