package com.xiaoneng.ss.common.utils.recyclerview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.xiaoneng.ss.R;

/**
 * @类名称: CLASS
 * @类描述:
 * @创建人：月月
 * @创建时间：2018/12/4 14:07
 * @备注：
 */
public class RefreshViewFooter extends LinearLayout implements RefreshFooter {
    private ImageView pull_to_refresh_image;
    private AnimationDrawable animationDrawable;
    public RefreshViewFooter(Context context) {
        this(context,null);
    }

    public RefreshViewFooter(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshViewFooter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.refresh_header, null);
        pull_to_refresh_image = view.findViewById(R.id.pull_to_refresh_image);
        pull_to_refresh_image.setImageResource(R.drawable.ic_add);
        animationDrawable = (AnimationDrawable) pull_to_refresh_image.getDrawable();
        addView(view);
    }


    @Override
    public void onReleased(RefreshLayout refreshLayout, int height, int extendHeight) {

    }


    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int height, int extendHeight) {
        animationDrawable.start();
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        animationDrawable.stop();//停止动画
        return 200;
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {

    }

    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        return false;
    }
}
