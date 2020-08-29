package com.xiaoneng.ss.common.utils.recyclerview;

/**
 * @Title 状态改变接口
 * @Author: Burning
 * @CreateDate: 2019-07-08 17:54
 */
public interface StatusChangeInterface {
    void showContentView();
    void showEmptyView();
    void showErrorView();
    void showLoadingView();
}
