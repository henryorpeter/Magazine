package com.juguo.magazine.adapter;

import com.juguo.magazine.R;
import com.juguo.magazine.databinding.ItemSlideModeBinding;
import com.zhpan.bannerview.BaseBannerAdapter;
import com.zhpan.bannerview.BaseViewHolder;

/**
 * @author yangjinjing
 * @date
 * Class 描述 : 使用ViewBinding
 */
public class ViewBindingSampleAdapter extends BaseBannerAdapter<Integer> {

  private final int mRoundCorner;

  public ViewBindingSampleAdapter(int roundCorner) {
    mRoundCorner = roundCorner;
  }

  @Override
  protected void bindData(BaseViewHolder<Integer> holder, Integer data, int position,
      int pageSize) {
    //示例使用ViewBinding
    ItemSlideModeBinding viewBinding = ItemSlideModeBinding.bind(holder.itemView);
    viewBinding.bannerImage.setRoundCorner(mRoundCorner);
    viewBinding.bannerImage.setImageResource(data);
  }

  @Override
  public int getLayoutId(int viewType) {
    return R.layout.item_slide_mode;
  }
}

