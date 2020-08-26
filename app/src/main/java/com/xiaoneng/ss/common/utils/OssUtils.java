package com.xiaoneng.ss.common.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.xiaoneng.ss.module.circular.model.StsTokenBean;

/**
 * @author Burning
 * @description:
 * @date :2020/8/26 10:11 AM
 */
class OssUtils {

    //同步上传文件方法

    private void uploadFile(Context context, StsTokenBean stsTokenBean) {

        //根据你的OSS的地区而自行定义，本文中的是杭州
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";

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
        PutObjectRequest put = new PutObjectRequest("<bucketName>", "<objectName>", "<uploadFilePath>");

// 文件元信息的设置是可选的。
// ObjectMetadata metadata = new ObjectMetadata();
// metadata.setContentType("application/octet-stream"); // 设置content-type。
// metadata.setContentMD5(BinaryUtil.calculateBase64Md5(uploadFilePath)); // 校验MD5。
// put.setMetadata(metadata);
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

    private void asyncUploadFile(Context context, StsTokenBean stsTokenBean) {
        //根据你的OSS的地区而自行定义，本文中的是杭州
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";

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
        PutObjectRequest put = new PutObjectRequest("<bucketName>", "<objectName>", "<uploadFilePath>");

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
            }
        });
// task.cancel(); // 可以取消任务。
// task.waitUntilFinished(); // 等待任务完成
    }

    //上传文件方法
    private void upload_file(Context context,StsTokenBean stsTokenBean) {
        String loacalFilePath = "";
        //根据你的OSS的地区而自行定义，本文中的是杭州
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";

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

        //当前时间戳，用于自定义文件在OSS中存储路径末尾的名称
        String image_url_time = System.currentTimeMillis() + "";

        // 构造上传请求,第二个数参是ObjectName，第三个参数是本地文件路径
        PutObjectRequest put = new PutObjectRequest("first-images", image_url_time, loacalFilePath);

        //异步上传可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {

                Log.i("上传进度：", "当前进度" + currentSize + "   总进度" + totalSize);

            }
        });

        //实现异步上传
        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());

                //这个image_url左边的字符串部分是我OSS的Bucket的文件存储地址，根据个人的文件存储地址不同，替换成自己的即可，而后面的image_url_time则是为了区分每个文件的文件名
                //注意，最好的方式是设置回调，因为回调的功能必须要在线上服务器才能测试，我服务器在本地环境中是不允许回调的
                //在咨询阿里云相关人员之后，他们说也允许记住地址，进行拼接的方式保存线上文件url路径
                //但是这种方式需要在OSS的管理控制台中将你的存储空间设置为公共读的方式，不然没法用下面的拼接链接。
                //此时你上传的文件所在的线上地址就已经获得了，想怎么使用则随意了
               String image_url = "http://first********ngzhou.aliyuncs.com/" + image_url_time;
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                if (clientException != null) {
                    // 本地异常，如网络异常等。
                    clientException.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常。
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
        // 等异步上传过程完成
        task.waitUntilFinished();
        Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
    }
}
