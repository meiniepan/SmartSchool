package com.xiaoneng.ss.common.utils.recyclerview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xiaoneng.ss.R;

import java.util.List;

import static java.lang.System.currentTimeMillis;


/**
 * @Title 带下拉刷新的RecyclerView
 * @Desc: 加载中、空布局、错误布局、正常显示
 * @Author: Burning
 * @CreateDate: 2019-07-06 17:27
 */

public class RefreshStatusRecyclerView extends SmartRefreshLayout implements StatusChangeInterface{
    private int currentPage;
    private int limitCountPerPage = 20;//每页数量
    private int startPage = -1;

    private StatusRecyclerView mRecyclerView;
    private boolean isNormalEnableRefresh;
    private boolean isNormalEnableLoadMore;

    public RefreshStatusRecyclerView(Context context) {
        this(context, null);
    }

    public RefreshStatusRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public RefreshStatusRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mRecyclerView = new StatusRecyclerView(context);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mRecyclerView.setLayoutParams(layoutParams);
        addView(mRecyclerView);
        setEnableRefresh(false);
        setEnableLoadMore(false);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RefreshStatusRecyclerView, 0, 0);
        try {
            int emptyLayoutRes = a.getResourceId(R.styleable.RefreshStatusRecyclerView_emptyLayout,0);
            int emptyViewDrawableRes = a.getResourceId(R.styleable.RefreshStatusRecyclerView_emptyViewDrawable,0);
            String emptyViewText = a.getString(R.styleable.RefreshStatusRecyclerView_emptyViewText);
            int errorLayoutRes = a.getResourceId(R.styleable.RefreshStatusRecyclerView_errorLayout,0);
            int errorViewDrawableRes = a.getResourceId(R.styleable.RefreshStatusRecyclerView_errorViewDrawable,0);
            String errorViewText = a.getString(R.styleable.RefreshStatusRecyclerView_errorViewText);
            int loadingLayoutRes = a.getResourceId(R.styleable.RefreshStatusRecyclerView_loadingLayout,0);
            String loadingViewText = a.getString(R.styleable.RefreshStatusRecyclerView_loadingViewText);
            mRecyclerView.setEmptyLayoutRes(emptyLayoutRes);
            mRecyclerView.setEmptyViewDrawableRes(emptyViewDrawableRes);
            mRecyclerView.setEmptyViewText(emptyViewText);
            mRecyclerView.setErrorLayoutRes(errorLayoutRes);
            mRecyclerView.setErrorViewDrawableRes(errorViewDrawableRes);
            mRecyclerView.setErrorViewText(errorViewText);
            mRecyclerView.setLoadingLayoutRes(loadingLayoutRes);
            mRecyclerView.setLoadingViewText(loadingViewText);
        } finally {
            a.recycle();
        }
    }

    public StatusRecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    /**
     * @param layoutManger 设置RV的LayoutManager
     */
    public void setLayoutManager(@Nullable RecyclerView.LayoutManager layoutManger) {
        mRecyclerView.setLayoutManager(layoutManger);
    }

    /**
     * 已废弃，请使用传数据源的方法
     * @param adapter
     */
    @Deprecated
    public void setAdapter(@Nullable RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    public void setAdapter(@Nullable BaseQuickAdapter adapter) {
        setAdapter(adapter,adapter.getData(),true);
    }

    /**
     * 绑定适配器
     * @param adapter   适配器
     * @param dataList  数据源
     */
    public <T>void setAdapter(@Nullable RecyclerView.Adapter adapter, List<T> dataList){
        mRecyclerView.setAdapter(adapter,dataList,true);
    }

    /**
     * 绑定适配器
     * @param adapter   适配器
     * @param dataList  数据源
     * @param enableAutoEmpty   在notify列表的时候是否根据数据自动显示空布局，默认为 true
     */
    public <T>void setAdapter(@Nullable RecyclerView.Adapter adapter, List<T> dataList, boolean enableAutoEmpty){
        mRecyclerView.setAdapter(adapter,dataList,enableAutoEmpty);
    }

    /**
     * 绑定适配器
     * @param adapter   适配器
     * @param dataList  数据源
     * @param startPage         开始的页码
     * @param limitCountPerPage 每页的数量
     * @param <T>
     */
    public <T>void setAdapter(@Nullable RecyclerView.Adapter adapter, List<T> dataList, int startPage, int limitCountPerPage){
        mRecyclerView.setAdapter(adapter,dataList,true);
        this.startPage = startPage;
        this.limitCountPerPage = limitCountPerPage;
        this.currentPage = startPage;
    }

    /**
     * 绑定适配器，同时判断此次设置的数据是否为空显示空布局
     * @param adapter   适配器
     * @param dataList  数据源
     */
    public <T>void setAdapterCheckEmpty(@Nullable RecyclerView.Adapter adapter, List<T> dataList){
        mRecyclerView.setAdapterCheckEmpty(adapter,dataList);
    }

    public int getCurrentPage(){
        return this.startPage <0?1:this.currentPage;
    }

    /**
     * 列表数据刷新，自动判断是否是空集合加载空view
     */
    public void notifyDataSetChange(){
        finishRefreshLoadMore();
        mRecyclerView.notifyDataSetChange(this);

        if ( startPage != -1
                && null!=mRecyclerView.getListData()
                && mRecyclerView.getListData().size() < (currentPage-startPage+1)*limitCountPerPage){
            setEnableLoadMore(false);
            finishLoadMoreWithNoMoreData();
            if (currentPage>startPage) Toast.makeText(mRecyclerView.getContext(),"已经加载到最后一页",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param onShowContentListener 刷新，添加内容展示监听（是否是空布局）
     */
    public void notifyDataSetChange(OnShowContentListener onShowContentListener){
        setOnShowContentListener(onShowContentListener);
        notifyDataSetChange();
    }

    /**
     * @param onShowContentListener 添加内容展示监听（是否是空布局）
     */
    public void setOnShowContentListener(OnShowContentListener onShowContentListener){
        mRecyclerView.setOnShowContentListener(onShowContentListener);
    }



    /**
     * @param onEmptyItemClickListener 设置空view的的整个item的点击监听
     */
    public void setOnEmptyItemClickListener(BaseQuickAdapter.OnItemClickListener onEmptyItemClickListener){
        mRecyclerView.setOnEmptyItemClickListener(onEmptyItemClickListener);
    }

    /**
     * 设置空view的子控件的点击监听
     * @param viewId    子view的id
     * @param onClickListener   点击监听
     */
    public void setOnEmptyChildClickListener(int viewId, View.OnClickListener onClickListener){
        mRecyclerView.setOnEmptyChildClickListener(viewId,onClickListener);
    }

    /**
     * @param onErrorItemClickListener 设置错误布局view的的整个item的点击监听
     */
    public void setOnErrorItemClickListener(BaseQuickAdapter.OnItemClickListener onErrorItemClickListener){
        mRecyclerView.setOnErrorItemClickListener(onErrorItemClickListener);
    }

    /**
     * 设置错误布局view的子控件的点击监听
     * @param viewId    子view的id
     * @param onClickListener   点击监听
     */
    public void setOnErrorChildClickListener(int viewId, View.OnClickListener onClickListener){
        mRecyclerView.setOnErrorChildClickListener(viewId,onClickListener);
    }

    public void setOnErrorRetryClickListener(View.OnClickListener onErrorRetryClickListener){
        mRecyclerView.setOnErrorRetryClickListener(onErrorRetryClickListener);
    }

    /**
     * 控制显示正常有数据的布局
     */
    @Override
    public void showContentView() {
        setEnableLoadMore(isNormalEnableLoadMore);
        setEnableRefresh(isNormalEnableRefresh);
        mRecyclerView.showContentView();
    }


    /**
     * 控制展示空布局
     */
    @Override
    public void showEmptyView() {
        setEnableLoadMore(false);
        mRecyclerView.showEmptyView();
    }

    /**
     * 控制显示错误布局
     */
    @Override
    public void showErrorView() {
        setEnableLoadMore(false);
        mRecyclerView.showErrorView();
    }

    /**
     * 控制展示加载进度布局
     */
    @Override
    public void showLoadingView() {
        mRecyclerView.showLoadingView();
    }

    @Override
    public RefreshLayout setEnableRefresh(boolean enabled) {
        isNormalEnableRefresh = enabled;
        return super.setEnableRefresh(enabled);
    }

    @Override
    public RefreshLayout setEnableLoadMore(boolean enabled) {
        isNormalEnableLoadMore = enabled;
        return super.setEnableLoadMore(enabled);
    }

    @Override
    public RefreshLayout setOnRefreshListener(OnRefreshListener listener) {
        isNormalEnableRefresh = true;
        setEnableRefresh(true);
        return super.setOnRefreshListener(listener);
    }

    @Override
    public RefreshLayout setOnLoadMoreListener(OnLoadMoreListener listener) {
        isNormalEnableLoadMore = true;
        setEnableLoadMore(true);
        return super.setOnLoadMoreListener(listener);
    }

    @Override
    public RefreshLayout setOnRefreshLoadMoreListener(OnRefreshLoadMoreListener listener) {
        isNormalEnableRefresh = true;
        isNormalEnableLoadMore = true;
        setEnableRefresh(true);
        setEnableLoadMore(true);
        return super.setOnRefreshLoadMoreListener(listener);
    }

    public void finishRefreshLoadMore() {
        finishRefresh();
        finishLoadMore();
    }






    /*-------------------------------  重写SmartRefreshLayout的方法，添加page ---------------------------*/

    @SuppressLint("RestrictedApi")
    @Override
    protected void setStateRefreshing(final boolean notify) {
        AnimatorListenerAdapter listener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLastOpenTime = currentTimeMillis();
                notifyStateChanged(RefreshState.Refreshing);
                if (mRefreshListener != null) {
                    if(notify) {
                        currentPage = startPage;
                        mRefreshListener.onRefresh(RefreshStatusRecyclerView.this);
                    }
                } else if (mOnMultiPurposeListener == null) {
                    finishRefresh(3000);
                }
                if (mRefreshHeader != null) {
                    mRefreshHeader.onStartAnimator(RefreshStatusRecyclerView.this, mHeaderHeight,  (int) (mHeaderMaxDragRate * mHeaderHeight));
                }
                if (mOnMultiPurposeListener != null && mRefreshHeader instanceof RefreshHeader) {
                    if (notify) {
                        mOnMultiPurposeListener.onRefresh(RefreshStatusRecyclerView.this);
                    }
                    mOnMultiPurposeListener.onHeaderStartAnimator((RefreshHeader) mRefreshHeader, mHeaderHeight,  (int) (mHeaderMaxDragRate * mHeaderHeight));
                }
            }
        };
        notifyStateChanged(RefreshState.RefreshReleased);
        ValueAnimator animator = mKernel.animSpinner(mHeaderHeight);
        if (animator != null) {
            animator.addListener(listener);
        }
        if (mRefreshHeader != null) {
            //onReleased 的执行顺序定在 animSpinner 之后 onAnimationEnd 之前
            // 这样 onRefreshReleased内部 可以做出 对 前面 animSpinner 的覆盖 操作
            mRefreshHeader.onReleased(this, mHeaderHeight,  (int) (mHeaderMaxDragRate * mHeaderHeight));
        }
        if (mOnMultiPurposeListener != null && mRefreshHeader instanceof RefreshHeader) {
            //同 mRefreshHeader.onReleased 一致
            mOnMultiPurposeListener.onHeaderReleased((RefreshHeader)mRefreshHeader, mHeaderHeight,  (int) (mHeaderMaxDragRate * mHeaderHeight));
        }
        if (animator == null) {
            //onAnimationEnd 会改变状态为 Refreshing 必须在 onReleased 之后调用
            listener.onAnimationEnd(null);
        }
    }


    /**
     * 直接将状态设置为 Loading 正在加载
     * @param triggerLoadMoreEvent 是否触发加载回调
     */
    @SuppressLint("RestrictedApi")
    @Override
    protected void setStateDirectLoading(boolean triggerLoadMoreEvent) {
        if (mState != RefreshState.Loading) {
            mLastOpenTime = currentTimeMillis();
//            if (mState != RefreshState.LoadReleased) {
//                if (mState != RefreshState.ReleaseToLoad) {
//                    if (mState != RefreshState.PullUpToLoad) {
//                        mKernel.setState(RefreshState.PullUpToLoad);
//                    }
//                    mKernel.setState(RefreshState.ReleaseToLoad);
//                }
//                notifyStateChanged(RefreshState.LoadReleased);
//                if (mRefreshFooter != null) {
//                    mRefreshFooter.onReleased(this, mFooterHeight, (int) (mFooterMaxDragRate * mFooterHeight));
//                }
//            }
            mFooterLocked = true;//Footer 正在loading 的时候是否锁住 列表不能向上滚动
            notifyStateChanged(RefreshState.Loading);
            if (mLoadMoreListener != null) {
                if (triggerLoadMoreEvent) {
                    currentPage++;
                    mLoadMoreListener.onLoadMore(this);
                }
            } else if (mOnMultiPurposeListener == null) {
                finishLoadMore(2000);//如果没有任何加载监听器，两秒之后自动关闭
            }
            if (mRefreshFooter != null) {
                mRefreshFooter.onStartAnimator(this, mFooterHeight, (int) (mFooterMaxDragRate * mFooterHeight));
            }
            if (mOnMultiPurposeListener != null && mRefreshFooter instanceof RefreshFooter) {
                final OnLoadMoreListener listener = mOnMultiPurposeListener;
                if (triggerLoadMoreEvent) {
                    listener.onLoadMore(this);
                }
                mOnMultiPurposeListener.onFooterStartAnimator((RefreshFooter) mRefreshFooter, mFooterHeight, (int) (mFooterMaxDragRate * mFooterHeight));
            }
        }
    }
    /**
     * 黏性移动 spinner
     * @param spinner 偏移量
     */
    @SuppressLint("RestrictedApi")
    @Override
    protected void moveSpinnerInfinitely(float spinner) {
        final View thisView = this;
        if (mNestedInProgress && !mEnableLoadMoreWhenContentNotFull && spinner < 0) {
            if (!mRefreshContent.canLoadMore()) {
                /*
                 * 2019-1-22 修复 嵌套滚动模式下 mEnableLoadMoreWhenContentNotFull=false 无效的bug
                 */
                spinner = 0;
            }
        }
        if (spinner > mScreenHeightPixels * 3 && thisView.getTag() == null) {
            String egg = "你这么死拉，臣妾做不到啊！";
            Toast.makeText(thisView.getContext(), egg, Toast.LENGTH_SHORT).show();
            thisView.setTag(egg);
        }
        if (mState == RefreshState.TwoLevel && spinner > 0) {
            mKernel.moveSpinner(Math.min((int) spinner, thisView.getMeasuredHeight()), true);
        } else if (mState == RefreshState.Refreshing && spinner >= 0) {
            if (spinner < mHeaderHeight) {
                mKernel.moveSpinner((int) spinner, true);
            } else {
                final double M = (mHeaderMaxDragRate - 1) * mHeaderHeight;
                final double H = Math.max(mScreenHeightPixels * 4 / 3, thisView.getHeight()) - mHeaderHeight;
                final double x = Math.max(0, (spinner - mHeaderHeight) * mDragRate);
                final double y = Math.min(M * (1 - Math.pow(100, -x / (H == 0 ? 1 : H))), x);// 公式 y = M(1-100^(-x/H))
                mKernel.moveSpinner((int) y + mHeaderHeight, true);
            }
        } else if (spinner < 0 && (mState == RefreshState.Loading
                || (mEnableFooterFollowWhenNoMoreData && mFooterNoMoreData && mFooterNoMoreDataEffective && isEnableRefreshOrLoadMore(mEnableLoadMore))
                || (mEnableAutoLoadMore && !mFooterNoMoreData && isEnableRefreshOrLoadMore(mEnableLoadMore)))) {
            if (spinner > -mFooterHeight) {
                mKernel.moveSpinner((int) spinner, true);
            } else {
                final double M = (mFooterMaxDragRate - 1) * mFooterHeight;
                final double H = Math.max(mScreenHeightPixels * 4 / 3, thisView.getHeight()) - mFooterHeight;
                final double x = -Math.min(0, (spinner + mFooterHeight) * mDragRate);
                final double y = -Math.min(M * (1 - Math.pow(100, -x / (H == 0 ? 1 : H))), x);// 公式 y = M(1-100^(-x/H))
                mKernel.moveSpinner((int) y - mFooterHeight, true);
            }
        } else if (spinner >= 0) {
            final double M = mHeaderMaxDragRate * mHeaderHeight;
            final double H = Math.max(mScreenHeightPixels / 2, thisView.getHeight());
            final double x = Math.max(0, spinner * mDragRate);
            final double y = Math.min(M * (1 - Math.pow(100, -x / (H == 0 ? 1 : H))), x);// 公式 y = M(1-100^(-x/H))
            mKernel.moveSpinner((int) y, true);
        } else {
            final double M = mFooterMaxDragRate * mFooterHeight;
            final double H = Math.max(mScreenHeightPixels / 2, thisView.getHeight());
            final double x = -Math.min(0, spinner * mDragRate);
            final double y = -Math.min(M * (1 - Math.pow(100, -x / (H == 0 ? 1 : H))), x);// 公式 y = M(1-100^(-x/H))
            mKernel.moveSpinner((int) y, true);
        }
        if (mEnableAutoLoadMore && !mFooterNoMoreData && isEnableRefreshOrLoadMore(mEnableLoadMore) && spinner < 0
                && mState != RefreshState.Refreshing
                && mState != RefreshState.Loading
                && mState != RefreshState.LoadFinish) {
            if (mDisableContentWhenLoading) {
                animationRunnable = null;
                mKernel.animSpinner(-mFooterHeight);
            }
            setStateDirectLoading(false);
            /*
             * 自动加载模式时，延迟触发 onLoadMore ，mReboundDuration 保证动画能顺利执行
             */
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mLoadMoreListener != null) {
                        currentPage++;
                        mLoadMoreListener.onLoadMore(RefreshStatusRecyclerView.this);
                    } else if (mOnMultiPurposeListener == null) {
                        finishLoadMore(2000);//如果没有任何加载监听器，两秒之后自动关闭
                    }
                    final OnLoadMoreListener listener = mOnMultiPurposeListener;
                    if (listener != null) {
                        listener.onLoadMore(RefreshStatusRecyclerView.this);
                    }
                }
            }, mReboundDuration);
        }
    }



}
