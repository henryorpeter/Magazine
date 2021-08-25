package com.juguo.magazine.remote;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juguo.magazine.App;
import com.juguo.magazine.R;
import com.juguo.magazine.bean.FavoritesListBean;
import com.juguo.magazine.bean.PieceBean;

import cn.lemon.view.adapter.BaseViewHolder;

public class FindRecordHolder extends BaseViewHolder<FavoritesListBean.Favorites> {

    private TextView beep;
    private ImageView imageViewUrl;
    private TextView textViewHot;

    public FindRecordHolder(ViewGroup parent) {
        super(parent, R.layout.find_consume);
    }

    @Override
    public void setData(final FavoritesListBean.Favorites photoBean) {
        super.setData(photoBean);
        //getsData();
        beep.setText(photoBean.getName());
        String viewHot = String.valueOf(photoBean.getViewTimes());
        textViewHot.setText(viewHot+"人阅读");
        //图片加载......
        String url = photoBean.getCoverImgUrl();
        Glide.with(App.getContext())
                .load(url)
                .into(imageViewUrl);
    }

    @Override
    public void onInitializeView() {
        super.onInitializeView();
        textViewHot = findViewById(R.id.textView7);
        beep = findViewById(R.id.textView6);
        imageViewUrl = findViewById(R.id.imageView7);
    }


}