package com.xiaoneng.ss.common.utils.recyclerview;

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
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.xiaoneng.ss.R;

import java.util.List;


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

    public void setNewData(List dataList) {
        mRecyclerView.setNewData(dataList);
    }

    /**
     * 绑定适配器
     * @param adapter   适配器
     * @param dataList  数据源
     */
    public void setAdapter(@Nullable RecyclerView.Adapter adapter, List dataList){
        mRecyclerView.setAdapter(adapter,dataList,true);
    }

    /**
     * 绑定适配器
     * @param adapter   适配器
     * @param dataList  数据源
     * @param enableAutoEmpty   在notify列表的时候是否根据数据自动显示空布局，默认为 true
     */
    public void setAdapter(@Nullable RecyclerView.Adapter adapter, List dataList, boolean enableAutoEmpty){
        mRecyclerView.setAdapter(adapter,dataList,enableAutoEmpty);
    }

    /**
     * 绑定适配器
     * @param adapter   适配器
     * @param dataList  数据源
     * @param startPage         开始的页码
     * @param limitCountPerPage 每页的数量
     * @param
     */
    public void setAdapter(@Nullable RecyclerView.Adapter adapter, List dataList, int startPage, int limitCountPerPage){
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
    public void setAdapterCheckEmpty(@Nullable RecyclerView.Adapter adapter, List dataList){
        mRecyclerView.setAdapterCheckEmpty(adapter,dataList);
    }

    public int getCurrentPage(){
        return this.startPage <0?1:this.currentPage;
    }

    /**
     * 列表数据刷新，自动判断是否是空集合加载空view
     */
    public void notifyDataSetChanged(){
        finishRefreshLoadMore();
        mRecyclerView.notifyDataSetChanged(this);

        if ( startPage != -1
                && null!=mRecyclerView.getListData()
                && mRecyclerView.getListData().size() < (currentPage-startPage+1)*limitCountPerPage){
            setEnableLoadMore(false);
            finishLoadMoreWithNoMoreData();
            if (currentPage>startPage) Toast.makeText(mRecyclerView.getContext(),"已经加载到最后一页",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 列表数据刷新，自动判断是否是空集合加载空view
     */
    public void showFinishLoadMore(){
            finishLoadMoreWithNoMoreData();
//            Toast.makeText(mRecyclerView.getContext(),R.string.load_more_end,Toast.LENGTH_SHORT).show();
    }

    /**
     * @param onShowContentListener 刷新，添加内容展示监听（是否是空布局）
     */
    public void notifyDataSetChanged(OnShowContentListener onShowContentListener){
        setOnShowContentListener(onShowContentListener);
        notifyDataSetChanged();
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



}
