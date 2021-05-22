package com.xiaoneng.ss.common.utils.oss;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
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
import com.alibaba.sdk.android.oss.model.ResumableUploadRequest;
import com.alibaba.sdk.android.oss.model.ResumableUploadResult;
import com.xiaoneng.ss.common.state.FileTransInfo;
import com.xiaoneng.ss.common.state.UserInfo;
import com.xiaoneng.ss.common.utils.eventBus.FileUploadEvent;
import com.xiaoneng.ss.model.StsTokenBean;
import com.xiaoneng.ss.module.school.model.DiskFileBean;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author Burning
 * @description:
 * @date :2020/8/26 10:11 AM
 */
public class OssUtils {
    static String END_POINT = UserInfo.INSTANCE.getUserBean().getDomain();
//    static String END_POINT = "oss-cn-beijing.aliyuncs.com";
    static String BUCKET = "xiaoneng";
    private static String OBJECT_KEY = "avatar/student/id/avatar";
    private static String FILE_PATH = "student/id/fileName";

    //同步上传文件方法

    public static void uploadFile(Context context, StsTokenBean stsTokenBean, String filePath) {

        String endpoint = UserInfo.INSTANCE.getUserBean().getDomain();

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

        String endpoint = UserInfo.INSTANCE.getUserBean().getDomain();

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
        if (endpoint ==null){
            listener.onFail();
            return;
        }
        OSS oss = new OSSClient(context, endpoint, credentialProvider, conf);
        // 构造上传请求。
        PutObjectRequest put = new PutObjectRequest(BUCKET, objectKey, filePath);

// 异步上传时可以设置进度回调。
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
                DiskFileBean bean = new DiskFileBean();
                bean.setPath(filePath);
                bean.setCurrentSize(currentSize);
                bean.setTotalSize(totalSize);
                new FileUploadEvent(bean).post();
            }
        });

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());
                listener.onSuccess();
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

    //异步上传文件方法

    public static void uploadResumeFile(Context context, StsTokenBean stsTokenBean, String objectKey, String filePath,OssListener listener) {

        String endpoint = UserInfo.INSTANCE.getUserBean().getDomain();
        String recordDirectory = Environment.getExternalStorageDirectory().getAbsolutePath() + "/oss_record/";

        File recordDir = new File(recordDirectory);

// 确保断点记录的保存文件夹已存在，如果不存在则新建断点记录的保存文件夹。
        if (!recordDir.exists()) {
            recordDir.mkdirs();
        }
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
        if (endpoint ==null){
            listener.onFail();
            return;
        }
        OSS oss = new OSSClient(context, endpoint, credentialProvider, conf);
        // 构造上传请求。
        ResumableUploadRequest request = new ResumableUploadRequest(BUCKET, objectKey, filePath,recordDirectory);
        request.setDeleteUploadOnCancelling(false);
// 异步上传时可以设置进度回调。
        request.setProgressCallback(new OSSProgressCallback<ResumableUploadRequest>() {
            @Override
            public void onProgress(ResumableUploadRequest request, long currentSize, long totalSize) {
                Log.d("resumableUpload====", "currentSize: " + currentSize + " totalSize: " + totalSize);
                DiskFileBean bean = new DiskFileBean();
                bean.setPath(filePath);
                bean.setObjectid(objectKey);
                bean.setCurrentSize(currentSize);
                bean.setTotalSize(totalSize);
                new FileUploadEvent(bean).post();
                FileTransInfo.INSTANCE.modifyFile(bean);
            }
        });
        // 异步调用断点上传。
        OSSAsyncTask resumableTask = oss.asyncResumableUpload(request, new OSSCompletedCallback<ResumableUploadRequest, ResumableUploadResult>() {
            @Override
            public void onSuccess(ResumableUploadRequest request, ResumableUploadResult result) {
                Log.d("resumableUpload====", "success!");
                listener.onSuccess();
                DiskFileBean bean = new DiskFileBean();
                bean.setPath(filePath);
                bean.setObjectid(objectKey);
                bean.setStatus(2);
                new FileUploadEvent(bean).post();
                  FileTransInfo.INSTANCE.modifyFile(bean);
            }

            @Override
            public void onFailure(ResumableUploadRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 异常处理。
                DiskFileBean bean = new DiskFileBean();
                bean.setPath(filePath);
                bean.setObjectid(objectKey);
                bean.setStatus(1);
                new FileUploadEvent(bean).post();
                FileTransInfo.INSTANCE.modifyFile(bean);
                Log.d("resumableUpload====", clientExcepion+" "+serviceException);
                listener.onFail();
            }
        });

        DiskFileBean bean = new DiskFileBean();
        bean.setPath(filePath);
        bean.setObjectid(objectKey);
        bean.setStatus(0);
        bean.setTask(resumableTask);
        new FileUploadEvent(bean).postSticky();

// task.cancel(); // 可以取消任务。
        resumableTask.waitUntilFinished(); // 等待任务完成


    }
    public static void downloadFile(Context context, StsTokenBean stsTokenBean, String objectKey, String filePath,OssListener listener) {
        String endpoint = UserInfo.INSTANCE.getUserBean().getDomain();
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
        if (endpoint ==null){
            listener.onFail();
            return;
        }
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
                    File filename = new File(filePath);
                    if (!filename.exists()) {
                        filename.createNewFile();
                    }
                    FileOutputStream fout = new FileOutputStream(filePath);
                    fout.write(buffer);
                    fout.close();
                    listener.onSuccess();
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

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

}
