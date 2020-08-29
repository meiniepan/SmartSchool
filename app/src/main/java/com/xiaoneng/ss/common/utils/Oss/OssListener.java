package com.xiaoneng.ss.common.utils.Oss;

/**
 * @author Burning
 * @description:
 * @date :2020/8/26 10:11 AM
 */
public interface OssListener {
    void onSuccess(String filePath);
    void onSuccess2(byte[] filePath);

    void onFail();
}
