package com.juguo.magazine.remote;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juguo.magazine.App;
import com.juguo.magazine.R;
import com.juguo.magazine.bean.PieceBean;

import cn.lemon.view.adapter.BaseViewHolder;

public class NewRecordHolder extends BaseViewHolder<PieceBean.Price> {

    private TextView beep;
    private ImageView imageViewUrl;

    public NewRecordHolder(ViewGroup parent) {
        super(parent, R.layout.news_consume);
    }

    @Override
    public void setData(final PieceBean.Price photoBean) {
        super.setData(photoBean);
        //getsData();
        beep.setText(photoBean.getName());
        //图片加载......
        String url = photoBean.getCoverImgUrl();
        Glide.with(App.getContext())
                .load(url)
                .into(imageViewUrl);
    }

    @Override
    public void onInitializeView() {
        super.onInitializeView();
        beep = findViewById(R.id.textView3);
        imageViewUrl = findViewById(R.id.imageView_new);
    }


}