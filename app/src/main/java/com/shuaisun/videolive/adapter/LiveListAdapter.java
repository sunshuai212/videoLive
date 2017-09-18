package com.shuaisun.videolive.adapter;

import android.media.Image;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuaisun.videolive.R;
import com.shuaisun.videolive.model.InKeEntity;

import java.util.List;

/**
 * Created by shuaisun on 2017/9/17.
 */

public class LiveListAdapter extends BaseQuickAdapter<InKeEntity.LivesBean,BaseViewHolder> {

    public LiveListAdapter(@Nullable List<InKeEntity.LivesBean> data) {
        super(R.layout.item_live_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InKeEntity.LivesBean item) {
        helper.setText(R.id.tv_name,item.getCreator().getNick());
        Glide.with(mContext).load(item.getCreator().getPortrait()).into((ImageView)helper.getView(R.id.iv_content));
    }
}
