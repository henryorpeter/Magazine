package com.juguo.magazine.remote;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juguo.magazine.App;
import com.juguo.magazine.R;
import com.juguo.magazine.bean.PieceBean;
import com.juguo.magazine.bean.ReadHistoryBean;

import cn.lemon.view.adapter.BaseViewHolder;

public class ReadRecordHolder extends BaseViewHolder<ReadHistoryBean.ReadHistory> {

    private TextView beep;
    private ImageView imageViewUrl;
    private TextView textViewHot;

    public ReadRecordHolder(ViewGroup parent) {
        super(parent, R.layout.read_consume);
    }

    @Override
    public void setData(final ReadHistoryBean.ReadHistory photoBean) {
        super.setData(photoBean);
        //getsData();
        beep.setText(photoBean.getName());
        String viewHot = String.valueOf(photoBean.getViewTimes());
        textViewHot.setText(viewHot+"人阅读");
        //图片加载......
        String url = photoBean.getCoverImgUrl();
        Glide.with(App.sInstance)
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