package com.juguo.magazine.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import com.juguo.magazine.bean.FavoritesListBean;
import com.juguo.magazine.bean.PieceBean;
import com.juguo.magazine.remote.FindRecordHolder;
import com.juguo.magazine.remote.HotRecordHolder;

import cn.lemon.view.adapter.BaseViewHolder;
import cn.lemon.view.adapter.RecyclerAdapter;


public class FindRecordAdapter extends RecyclerAdapter<FavoritesListBean.Favorites> {

    private OnItemClickListener onItemClickListener;
    public FindRecordAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder<FavoritesListBean.Favorites> onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new FindRecordHolder(parent);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<FavoritesListBean.Favorites> holder, int position) {
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
        void onItemClick(FavoritesListBean.Favorites data);
    }
}