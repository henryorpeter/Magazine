package com.juguo.magazine.remote;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juguo.magazine.App;
import com.juguo.magazine.R;
import com.juguo.magazine.bean.CategoryBean;

import cn.lemon.view.adapter.BaseViewHolder;

public class ClassifitionRecordHolder extends BaseViewHolder<CategoryBean.Category> {

    private TextView beep;
    private ImageView imageViewUrl;

    public ClassifitionRecordHolder(ViewGroup parent) {
        super(parent, R.layout.classification_consume);
    }

    @Override
    public void setData(final CategoryBean.Category photoBean) {
        super.setData(photoBean);
        //getsData();
        beep.setText(photoBean.getName());
        //图片加载......
        String url = photoBean.getDetail();
        Glide.with(App.sInstance)
                .load(url)
                .into(imageViewUrl);
    }

    @Override
    public void onInitializeView() {
        super.onInitializeView();
        beep = findViewById(R.id.textView4);
        imageViewUrl = findViewById(R.id.imageView9);
    }


}