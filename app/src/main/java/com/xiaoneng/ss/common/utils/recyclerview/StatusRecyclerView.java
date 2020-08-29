package com.xiaoneng.ss.common.utils.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xiaoneng.ss.R;
import com.xiaoneng.ss.common.utils.recyclerview.adapter.StatusEmptyAdapter;
import com.xiaoneng.ss.common.utils.recyclerview.adapter.StatusErrorAdapter;
import com.xiaoneng.ss.common.utils.recyclerview.adapter.StatusProgressAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Title 带状态的RecyclerView
 * @Desc: 加载中、空布局、错误布局、正常显示
 * @Author: Burning
 * @CreateDate: 2019-07-06 17:27
 */
public class StatusRecyclerView<T> extends RecyclerView implements StatusChangeInterface{
    private StatusEmptyAdapter emptyViewAdapter;
    private StatusErrorAdapter errorViewAdapter;
    private StatusProgressAdapter progressViewAdapter;
    private RecyclerView.Adapter dataAdapter;

    private LayoutManager mLayoutManager;

    private BaseQuickAdapter.OnItemClickListener onEmptyItemClickListener;
    private HashMap<Integer, View.OnClickListener> emptyChildClickListenerMap;

    private BaseQuickAdapter.OnItemClickListener onErrorItemClickListener;
    private HashMap<Integer, View.OnClickListener> errorChildClickListenerMap;

    private View.OnClickListener onErrorRetryClickListener;

    private int emptyLayoutRes;
    private int emptyViewDrawableRes;
    private String emptyViewText;

    private int errorLayoutRes;
    private int errorViewDrawableRes;
    private String errorViewText;

    private int loadingLayoutRes;
    private String loadingViewText;

    private List<T> dataList ;
    private boolean enableAutoEmpty = true;

    private OnShowContentListener onShowContentListener;


    public StatusRecyclerView(Context context) {
        this(context, null);
    }

    public StatusRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StatusRecyclerView, 0, 0);
        try {
            emptyLayoutRes = a.getResourceId(R.styleable.StatusRecyclerView_emptyLayout,0);
            emptyViewDrawableRes = a.getResourceId(R.styleable.StatusRecyclerView_emptyViewDrawable,0);
            emptyViewText = a.getString(R.styleable.StatusRecyclerView_emptyViewText);

            errorLayoutRes = a.getResourceId(R.styleable.StatusRecyclerView_errorLayout,0);
            errorViewDrawableRes = a.getResourceId(R.styleable.StatusRecyclerView_errorViewDrawable,0);
            errorViewText = a.getString(R.styleable.StatusRecyclerView_errorViewText);

            loadingLayoutRes = a.getResourceId(R.styleable.StatusRecyclerView_loadingLayout,0);
            loadingViewText = a.getString(R.styleable.StatusRecyclerView_loadingViewText);
        } finally {
            a.recycle();
        }
    }

    public StatusRecyclerView<T> setEmptyLayoutRes(int emptyLayoutRes) {
        this.emptyLayoutRes = emptyLayoutRes;
        return this;
    }

    public StatusRecyclerView<T> setEmptyViewDrawableRes(int emptyViewDrawableRes) {
        this.emptyViewDrawableRes = emptyViewDrawableRes;
        return this;
    }

    public StatusRecyclerView<T> setEmptyViewText(String emptyViewText) {
        this.emptyViewText = emptyViewText;
        return this;
    }

    public StatusRecyclerView<T> setErrorLayoutRes(int errorLayoutRes) {
        this.errorLayoutRes = errorLayoutRes;
        return this;
    }

    public StatusRecyclerView<T> setErrorViewDrawableRes(int errorViewDrawableRes) {
        this.errorViewDrawableRes = errorViewDrawableRes;
        return this;
    }

    public StatusRecyclerView<T> setErrorViewText(String errorViewText) {
        this.errorViewText = errorViewText;
        return this;
    }

    public StatusRecyclerView<T> setLoadingLayoutRes(int loadingLayoutRes) {
        this.loadingLayoutRes = loadingLayoutRes;
        return this;
    }

    public StatusRecyclerView<T> setLoadingViewText(String loadingViewText) {
        this.loadingViewText = loadingViewText;
        return this;
    }

    @Override
    public void setLayoutManager(@Nullable LayoutManager layout) {
        super.setLayoutManager(layout);
        this.mLayoutManager = layout;
    }

    /**
     * 已废弃，请使用传数据源的方法
     * @param adapter
     */
    @Deprecated
    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        setAdapter(adapter,null,true);
    }

    public void setAdapter(@Nullable BaseQuickAdapter adapter) {
        setAdapter(adapter,adapter.getData(),true);
    }

    /**
     * 绑定适配器
     * @param adapter   适配器
     * @param dataList  数据源
     */
    public void setAdapter(@Nullable Adapter adapter, List<T> dataList){
        setAdapter(adapter,dataList,true);
    }

    /**
     * 绑定适配器
     * @param adapter   适配器
     * @param dataList  数据源
     * @param enableAutoEmpty   在notify列表的时候是否根据数据自动显示空布局，默认为 true
     */
    public void setAdapter(@Nullable Adapter adapter, List<T> dataList, boolean enableAutoEmpty){
        super.setAdapter(adapter);
        this.dataAdapter = adapter;
        this.dataList = dataList;
        this.enableAutoEmpty = enableAutoEmpty;
        /*if(null!=dataList&&dataList.size()==0){
            notifyDataSetChange();
        }*/
    }

    /**
     * 绑定适配器，同时判断此次设置的数据是否为空显示空布局
     * @param adapter   适配器
     * @param dataList  数据源
     */
    public void setAdapterCheckEmpty(@Nullable Adapter adapter, List<T> dataList){
        setAdapter(adapter,dataList);
        if(null!=dataList&&dataList.size()==0){
            notifyDataSetChange();
        }
    }

    public List<T> getListData(){
        return this.dataList;
    }

    /**
     * 列表数据刷新，自动判断是否是空集合加载空view
     */
    public void notifyDataSetChange(){
        notifyDataSetChange(this);
    }

    public void notifyDataSetChange(StatusChangeInterface statusChangeInterface){
        if(null==dataList || !enableAutoEmpty){
            getAdapter().notifyDataSetChanged();
        }else {
            if(dataList.size()==0){//展示空布局
                statusChangeInterface.showEmptyView();
            }else {
                statusChangeInterface.showContentView();
            }
        }
    }

    public void notifyDataSetChange(OnShowContentListener onShowContentListener){
        this.onShowContentListener = onShowContentListener;
        notifyDataSetChange();
    }

    public void setOnShowContentListener(OnShowContentListener onShowContentListener){
        this.onShowContentListener = onShowContentListener;
    }


    /**
     * @param onEmptyItemClickListener 设置空view的的整个item的点击监听
     */
    public void setOnEmptyItemClickListener(BaseQuickAdapter.OnItemClickListener onEmptyItemClickListener){
        this.onEmptyItemClickListener = onEmptyItemClickListener;
    }

    /**
     * 设置空view的子控件的点击监听
     * @param viewId    子view的id
     * @param onClickListener   点击监听
     */
    public void setOnEmptyChildClickListener(int viewId, View.OnClickListener onClickListener){
        if(null!=emptyViewAdapter){
            View view = emptyViewAdapter.getViewByPosition(this,0,viewId);
            if(null!=view) view.setOnClickListener(onClickListener);
        }else {
            if(null==emptyChildClickListenerMap) emptyChildClickListenerMap = new HashMap<>();
            emptyChildClickListenerMap.put(viewId,onClickListener);
        }
    }

    /**
     * @param onErrorItemClickListener 设置错误布局view的的整个item的点击监听
     */
    public void setOnErrorItemClickListener(BaseQuickAdapter.OnItemClickListener onErrorItemClickListener){
        this.onErrorItemClickListener = onErrorItemClickListener;
    }

    /**
     * 设置错误布局view的子控件的点击监听
     * @param viewId    子view的id
     * @param onClickListener   点击监听
     */
    public void setOnErrorChildClickListener(int viewId, View.OnClickListener onClickListener){
        if(null!=errorViewAdapter){
            View view = errorViewAdapter.getViewByPosition(this,0,viewId);
            if(null!=view) view.setOnClickListener(onClickListener);
        }else {
            if(null==errorChildClickListenerMap) errorChildClickListenerMap = new HashMap<>();
            errorChildClickListenerMap.put(viewId,onClickListener);
        }
    }

    public void setOnErrorRetryClickListener(View.OnClickListener onErrorRetryClickListener){
        this.onErrorRetryClickListener = onErrorRetryClickListener;
    }

    /**
     * 控制显示正常有数据的布局
     */
    @Override
    public void showContentView() {
        if (getAdapter() instanceof StatusEmptyAdapter
                || getAdapter() instanceof StatusErrorAdapter
                || getAdapter() instanceof StatusProgressAdapter
        ) {
            super.setLayoutManager(mLayoutManager);
            super.setAdapter(dataAdapter);
        } else {
            dataAdapter.notifyDataSetChanged();
        }
        if (null!= onShowContentListener) onShowContentListener.onShowContent(false);
    }


    /**
     * 控制展示空布局
     */
    @Override
    public void showEmptyView() {
        if(null == emptyViewAdapter){
            List<StatusRvEntity> emptyList = new ArrayList<>();
            StatusRvEntity statusRvEntity = new StatusRvEntity(emptyViewText,emptyViewDrawableRes);
            emptyList.add(statusRvEntity);
            if(emptyLayoutRes == 0) emptyLayoutRes = R.layout.base_status_view_empty;
            emptyViewAdapter = new StatusEmptyAdapter(emptyLayoutRes, emptyList);
            emptyViewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if(null!=onEmptyItemClickListener) onEmptyItemClickListener.onItemClick(adapter,view,position);
                }
            });
        }

        super.setLayoutManager(new LinearLayoutManager(getContext()));
        super.setAdapter(emptyViewAdapter);
        if (null!=emptyChildClickListenerMap){
            for (Integer key: emptyChildClickListenerMap.keySet()){
                // TODO: 2019-07-08 有header的时候position是否有问题
                View view = emptyViewAdapter.getViewByPosition(this,0,key);
                if (null!=view) view.setOnClickListener(emptyChildClickListenerMap.get(key));
            }
            emptyChildClickListenerMap.clear();
            emptyChildClickListenerMap = null;
        }

        if (null!= onShowContentListener) onShowContentListener.onShowContent(true);
    }

    /**
     * 控制显示错误布局
     */
    @Override
    public void showErrorView() {
        if(null == errorViewAdapter){
            List<StatusRvEntity> errorList = new ArrayList<>();
            StatusRvEntity statusRvEntity = new StatusRvEntity(errorViewText,errorViewDrawableRes);
            errorList.add(statusRvEntity);
            if(errorLayoutRes == 0) errorLayoutRes = R.layout.status_view_error;
            errorViewAdapter = new StatusErrorAdapter(errorLayoutRes, errorList);
            errorViewAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if(null!=onErrorItemClickListener) onErrorItemClickListener.onItemClick(adapter,view,position);
                }
            });
        }
        super.setLayoutManager(new LinearLayoutManager(getContext()));
        super.setAdapter(errorViewAdapter);

        if (null!=errorChildClickListenerMap){
            for (Integer key: errorChildClickListenerMap.keySet()){
                // TODO: 2019-07-08 有header的时候position是否有问题
                View view = errorViewAdapter.getViewByPosition(this,0,key);
                if (null!=view) view.setOnClickListener(errorChildClickListenerMap.get(key));
            }
            errorChildClickListenerMap.clear();
            errorChildClickListenerMap = null;
        }
        if(null != onErrorRetryClickListener){
            View view = errorViewAdapter.getViewByPosition(this,0,R.id.tv_error_click);
            if (null!=view) view.setOnClickListener(onErrorRetryClickListener);
        }
    }

    /**
     * 控制展示加载进度布局
     */
    @Override
    public void showLoadingView() {
        if(null == progressViewAdapter){
            List<StatusRvEntity> progressList = new ArrayList<>();
            StatusRvEntity statusRvEntity = new StatusRvEntity(loadingViewText,0);
            progressList.add(statusRvEntity);
            if (loadingLayoutRes == 0) loadingLayoutRes = R.layout.status_view_progress;
            progressViewAdapter = new StatusProgressAdapter(loadingLayoutRes, progressList);
        }
        super.setLayoutManager(new LinearLayoutManager(getContext()));
        super.setAdapter(progressViewAdapter);
    }

}
