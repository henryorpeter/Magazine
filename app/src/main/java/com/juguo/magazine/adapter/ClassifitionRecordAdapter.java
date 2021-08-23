package com.juguo.magazine.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.juguo.magazine.bean.CategoryBean;
import com.juguo.magazine.bean.PieceBean;
import com.juguo.magazine.remote.ClassifitionRecordHolder;
import com.juguo.magazine.remote.NewRecordHolder;

import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.RecyclerAdapter;


public class ClassifitionRecordAdapter extends RecyclerAdapter<CategoryBean.Category> {

    private OnItemClickListener onItemClickListener;
    public ClassifitionRecordAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder<CategoryBean.Category> onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ClassifitionRecordHolder(parent);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<CategoryBean.Category> holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(holder.getData());
            }
            Log.e("TAG", "ddddd" + holder.getData());

        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(CategoryBean.Category data);
    }
}