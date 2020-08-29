package com.xiaoneng.ss.common.utils.recyclerview.adapter;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaoneng.ss.R;
import com.xiaoneng.ss.common.utils.recyclerview.StatusRvEntity;

import java.util.List;

/**
 * Created by meiniepan on 2018/2/5.
 */

public class StatusErrorAdapter extends BaseQuickAdapter<StatusRvEntity, BaseViewHolder> {


    public StatusErrorAdapter(int layoutResId, @Nullable List<StatusRvEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StatusRvEntity item) {
        if (!TextUtils.isEmpty(item.msg)) {
            TextView tv_empty = helper.getView(R.id.tv_error);
            if(null!=tv_empty) tv_empty.setText(item.msg);
        }
        if (item.drawableRes != 0) {
            ImageView imageView = helper.getView(R.id.iv_error);
            if(null!=imageView) imageView.setImageResource(item.drawableRes);
        }
    }

}
