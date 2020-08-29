package com.xiaoneng.ss.common.utils.recyclerview.adapter;

import android.text.TextUtils;
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

public class StatusProgressAdapter extends BaseQuickAdapter<StatusRvEntity, BaseViewHolder> {


    public StatusProgressAdapter(int layoutResId, @Nullable List<StatusRvEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StatusRvEntity item) {
        if (!TextUtils.isEmpty(item.msg)) {
            TextView tv = helper.getView(R.id.tv_loading);
            if(null!=tv) tv.setText(item.msg);
        }
    }
}
