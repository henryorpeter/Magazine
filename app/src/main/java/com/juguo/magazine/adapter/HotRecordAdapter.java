package com.juguo.magazine.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.juguo.magazine.bean.PieceBean;
import com.juguo.magazine.remote.HotRecordHolder;
import com.juguo.magazine.remote.NewRecordHolder;

import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.RecyclerAdapter;


public class HotRecordAdapter extends RecyclerAdapter<PieceBean.Price> {

    private OnItemClickListener onItemClickListener;
    public HotRecordAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder<PieceBean.Price> onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new HotRecordHolder(parent);
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

    @Override
    public int getItemCount() {
        return 3;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(PieceBean.Price data);
    }
}