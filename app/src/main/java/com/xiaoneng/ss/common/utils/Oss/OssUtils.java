package com.xiaoneng.ss.common.utils.Oss;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.xiaoneng.ss.model.StsTokenBean;

import java.io.FileOutputStream;

/**
 * @author Burning
 * @description:
 * @date :2020/8/26 10:11 AM
 */
public class OssUtils {
    static String END_POINT = "oss-cn-beijing.aliyuncs.com";
    static String BUCKET = "xiaoneng";
    private static String OBJECT_KEY = "avatar/student/id/avatar";
    private static String FILE_PATH = "student/id/fileName";

    //同步上传文件方法

    public static void uploadFile(Context context, StsTokenBean stsTokenBean, String filePath) {

        String endpoint = END_POINT;

        //移动端建议使用该方式，此时，stsToken中的前三个参数就派上用场了
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(stsTokenBean.getAccessKeyId(), stsTokenBean.getAccessKeySecret(), stsTokenBean.getSecurityToken());

        // 配置类如果不设置，会有默认配置。
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000);   // 连接超时，默认15秒。
        conf.setSocketTimeout(15 * 1000);       // socket超时，默认15秒。
        conf.setMaxConcurrentRequest(5);        // 最大并发请求数，默认5个。
        conf.setMaxErrorRetry(2);               // 失败后最大重试次数，默认2次。

        OSS oss = new OSSClient(context, endpoint, credentialProvider, conf);

        // 构造上传请求。
        PutObjectRequest put = new PutObjectRequest(BUCKET, "Burning", filePath);

        try {
            PutObjectResult putResult = oss.putObject(put);

            Log.d("PutObject", "UploadSuccess");
            Log.d("ETag", putResult.getETag());
            Log.d("RequestId", putResult.getRequestId());
        } catch (ClientException e) {
            // 本地异常，如网络异常等。
            e.printStackTrace();
        } catch (ServiceException e) {
            // 服务异常。
            Log.e("RequestId", e.getRequestId());
            Log.e("ErrorCode", e.getErrorCode());
            Log.e("HostId", e.getHostId());
            Log.e("RawMessage", e.getRawMessage());
        }
    }

    //异步上传文件方法

    public static void asyncUploadFile(Context context, StsTokenBean stsTokenBean, String objectKey, String filePath,OssListener listener) {

        String endpoint = END_POINT;

        //移动端建议使用该方式，此时，stsToken中的前三个参数就派上用场了
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(stsTokenBean.getAccessKeyId(), stsTokenBean.getAccessKeySecret(), stsTokenBean.getSecurityToken());

        // 配置类如果不设置，会有默认配置。
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000);   // 连接超时，默认15秒。
        conf.setSocketTimeout(15 * 1000);       // socket超时，默认15秒。
        conf.setMaxConcurrentRequest(5);        // 最大并发请求数，默认5个。
        conf.setMaxErrorRetry(2);               // 失败后最大重试次数，默认2次。

        //初始化OSS服务的客户端oss
        //事实上，初始化OSS的实例对象，应该具有与整个应用程序相同的生命周期，在应用程序生命周期结束时销毁
        //但这里只是实现功能，若时间紧，你仍然可以按照本文方式先将功能实现，然后优化
        OSS oss = new OSSClient(context, endpoint, credentialProvider, conf);
        // 构造上传请求。
        PutObjectRequest put = new PutObjectRequest(BUCKET, objectKey, filePath);

// 异步上传时可以设置进度回调。
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());
                listener.onSuccess(filePath);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常。
                if (clientExcepion != null) {
                    // 本地异常，如网络异常等。
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常。
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
                listener.onFail();
            }
        });
// task.cancel(); // 可以取消任务。
// task.waitUntilFinished(); // 等待任务完成
    }

    public static void downloadFile(Context context, StsTokenBean stsTokenBean, String objectKey, String filePath,OssListener listener) {
        String endpoint = END_POINT;
        //移动端建议使用该方式，此时，stsToken中的前三个参数就派上用场了
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(stsTokenBean.getAccessKeyId(), stsTokenBean.getAccessKeySecret(), stsTokenBean.getSecurityToken());

        // 配置类如果不设置，会有默认配置。
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000);   // 连接超时，默认15秒。
        conf.setSocketTimeout(15 * 1000);       // socket超时，默认15秒。
        conf.setMaxConcurrentRequest(5);        // 最大并发请求数，默认5个。
        conf.setMaxErrorRetry(2);               // 失败后最大重试次数，默认2次。

        //初始化OSS服务的客户端oss
        //事实上，初始化OSS的实例对象，应该具有与整个应用程序相同的生命周期，在应用程序生命周期结束时销毁
        //但这里只是实现功能，若时间紧，你仍然可以按照本文方式先将功能实现，然后优化
        OSS oss = new OSSClient(context, endpoint, credentialProvider, conf);

        //下载文件。
//objectKey等同于objectname，表示从OSS下载文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        GetObjectRequest get = new GetObjectRequest(BUCKET, objectKey);

        oss.asyncGetObject(get, new OSSCompletedCallback<GetObjectRequest, GetObjectResult>() {
            @Override
            public void onSuccess(GetObjectRequest request, GetObjectResult result) {
//开始读取数据。
                Log.e("Succcccccc", "serviceException.getErrorCode()");
                long length = result.getContentLength();
                byte[] buffer = new byte[(int) length];
                int readCount = 0;
                while (readCount < length) {
                    try {
                        readCount += result.getObjectContent().read(buffer, readCount, (int) length - readCount);
                    } catch (Exception e) {
                        OSSLog.logInfo(e.toString());
                    }
                }
//将下载后的文件存放在指定的本地路径。
                try {
                    FileOutputStream fout = new FileOutputStream(filePath);
                    fout.write(buffer);
                    fout.close();
                    listener.onSuccess2(buffer);
                } catch (Exception e) {
                    OSSLog.logInfo(e.toString());
                }
            }

            @Override
            public void onFailure(GetObjectRequest request, ClientException clientException,
                                  ServiceException serviceException) {
                listener.onFail();
            }
        });
    }
}
