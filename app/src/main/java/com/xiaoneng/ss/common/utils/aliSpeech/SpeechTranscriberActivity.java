package com.xiaoneng.ss.common.utils.aliSpeech;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.idst.nui.AsrResult;
import com.alibaba.idst.nui.CommonUtils;
import com.alibaba.idst.nui.Constants;
import com.alibaba.idst.nui.INativeNuiCallback;
import com.alibaba.idst.nui.KwsResult;
import com.alibaba.idst.nui.NativeNui;
import com.google.gson.Gson;
import com.xiaoneng.ss.R;
import com.xiaoneng.ss.base.view.BaseLifeCycleActivity;
import com.xiaoneng.ss.base.viewmodel.BaseViewModel;

import java.util.concurrent.atomic.AtomicBoolean;


public class SpeechTranscriberActivity<T extends BaseViewModel<?>> extends BaseLifeCycleActivity<T> implements INativeNuiCallback {
    private static final String TAG = "DemoMainActivity";

    final static int WAVE_FRAM_SIZE = 20 * 2 * 1 * 16000 / 1000; //20ms audio for 16k/16bit/mono
    public final static int SAMPLE_RATE = 16000;
    private AudioRecord mAudioRecorder;
    private AtomicBoolean vadMode = new AtomicBoolean(false);


    private HandlerThread mHanderThread;

    private boolean mInit = false;
    private Handler mHandler;
    private MediaPlayer mp;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (CommonUtils.copyAssetsData(this)) {
            Log.i(TAG, "copy assets data done");
        } else {
            Log.i(TAG, "copy assets failed");
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // ?????????????????????????????????
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            // ?????????????????? ?????? GRANTED---??????  DINIED---??????
            if (i != PackageManager.PERMISSION_GRANTED) {
                // ??????????????????????????????????????????????????????
                this.requestPermissions(permissions, 321);
            }
            while (true) {
                i = ContextCompat.checkSelfPermission(this, permissions[0]);
                // ?????????????????? ?????? GRANTED---??????  DINIED---??????
                if (i == PackageManager.PERMISSION_GRANTED)
                    break;
            }
        }
        initUIWidgets();


        mHanderThread = new HandlerThread("process_thread");
        mHanderThread.start();
        mHandler = new Handler(mHanderThread.getLooper());
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart");
        super.onStart();
        doInit();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop");
        super.onStop();
        NativeNui.GetInstance().release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initUIWidgets() {
//        asrView = (TextView) findViewById(R.id.textView);
//        kwsView = (TextView) findViewById(R.id.kws_text);
//
//        startButton = (Button) findViewById(R.id.button_start);
//        cancelButton = (Button) findViewById(R.id.button_cancel);
//
//        mVadSwitch = (Switch) findViewById(R.id.vad_switch);
//
//        mVadSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                Log.i(TAG, "vad mode onCheckedChanged b=" + b);
//                vadMode.set(b);
//            }
//        });
//
//
//        setButtonState(startButton, true);
//        setButtonState(cancelButton, false);
//        startButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "start!!!");
//
//                setButtonState(startButton, false);
//                setButtonState(cancelButton, true);
//                showText(asrView, "");
//                showText(kwsView, "");
//                startDialog();
//            }
//        });
//
//        cancelButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG, "cancel");
//                if (!checkNotInitToast()) {
//                    return;
//                }
//
//                setButtonState(startButton, true);
//                setButtonState(cancelButton, false);
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        long ret = NativeNui.GetInstance().stopDialog();
//                        Log.i(TAG, "cancel dialog " + ret + " end");
//                    }
//                });
//
//            }
//        });
    }

    public void doStart() {
        startDialog();
    }
    public void doStop() {
        if (!checkNotInitToast()) {
            return;
        }

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                long ret = NativeNui.GetInstance().stopDialog();
                Log.i(TAG, "cancel dialog " + ret + " end");
            }
        });
    }
    public EditText getEditText() {
        return new EditText(this);
    }

    private void doInit() {

        //??????????????????
        String assets_path = CommonUtils.getModelPath(this);
        Log.i(TAG, "use workspace " + assets_path);


        String debug_path = getExternalCacheDir().getAbsolutePath() + "/debug_" + System.currentTimeMillis();
        Utils.createDir(debug_path);

        //????????????????????????????????????????????????16bit/???????????????????????????8K/16K
        mAudioRecorder = new AudioRecord(MediaRecorder.AudioSource.CAMCORDER, SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, WAVE_FRAM_SIZE * 4);

        //?????????SDK????????????????????????Auth.getAliYunTicket???????????????ID????????????????????????
        int ret = NativeNui.GetInstance().initialize(this, genInitParams(assets_path, debug_path), Constants.LogLevel.LOG_LEVEL_VERBOSE, true);
        Log.i(TAG, "result = " + ret);
        if (ret == Constants.NuiResultCode.SUCCESS) {
            mInit = true;
        }

        //???????????????????????????????????????API??????
        NativeNui.GetInstance().setParams(genParams());
    }

    private String genParams() {
        String params = "";
        try {
            JSONObject nls_config = new JSONObject();
            nls_config.put("enable_intermediate_result", true);
//            ???????????????????????????????????????
//            nls_config.put("enable_punctuation_prediction", true);
//            nls_config.put("enable_inverse_text_normalization", true);
//            nls_config.put("max_sentence_silence", 800);
//            nls_config.put("enable_words", false);
//            nls_config.put("sample_rate", 16000);
//            nls_config.put("sr_format", "opus");
            JSONObject tmp = new JSONObject();
            tmp.put("nls_config", nls_config);
            tmp.put("service_type", Constants.kServiceTypeSpeechTranscriber);
//            ?????????HttpDns??????????????????
//            tmp.put("direct_ip", Utils.getDirectIp());
            params = tmp.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params;
    }

    private void startDialog() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                int ret = NativeNui.GetInstance().startDialog(Constants.VadMode.TYPE_P2T,
                        genDialogParams());
                Log.i(TAG, "start done with " + ret);
            }
        });
    }

    private String genInitParams(String workpath, String debugpath) {
        String str = "";
        try {
            JSONObject object = Auth.getAliYunTicket();
            object.put("url", "wss://nls-gateway.cn-shanghai.aliyuncs.com:443/ws/v1");
            object.put("workspace", workpath);
            object.put("debug_path", debugpath);
            str = object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "InsideUserContext:" + str);
        return str;
    }

    private String genDialogParams() {
        String params = "";
        try {
            JSONObject dialog_param = new JSONObject();
            params = dialog_param.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "dialog params: " + params);
        return params;
    }

    private boolean checkNotInitToast() {
        if (!mInit) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SpeechTranscriberActivity.this, "not init yet", Toast.LENGTH_LONG).show();
                }
            });
            return false;
        } else {
            return true;
        }
    }

    private void setButtonState(final Button btn, final boolean state) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "setBtn state " + btn.getText() + " state=" + state);
                btn.setEnabled(state);
            }
        });
    }

    private void showText(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "showText text=" + text);
                if (TextUtils.isEmpty(text)) {

                } else {
                    Gson gson = new Gson();
                    ResultBean bean = gson.fromJson(text,ResultBean.class);
                    getEditText().append(bean.getPayload().getResult());
                }
            }
        });
    }

    private void appendText(final TextView who, final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "append text=" + text);
                if (TextUtils.isEmpty(text)) {
                    return;
                } else {
                    String orign = who.getText().toString();
                    who.setText(orign + "\n---\n" + text);
                }
            }
        });
    }

    //??????????????????????????????
    @Override
    public void onNuiEventCallback(Constants.NuiEvent event, final int resultCode, final int arg2, KwsResult kwsResult,
                                   AsrResult asrResult) {
        Log.i(TAG, "event=" + event);
        if (event == Constants.NuiEvent.EVENT_ASR_RESULT) {
            showText(asrResult.asrResult);
        } else if (event == Constants.NuiEvent.EVENT_ASR_PARTIAL_RESULT || event == Constants.NuiEvent.EVENT_SENTENCE_END) {
            showText(asrResult.asrResult);
        } else if (event == Constants.NuiEvent.EVENT_ASR_ERROR) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SpeechTranscriberActivity.this, "ERROR with " + resultCode,
                            Toast.LENGTH_SHORT).show();
                }
            });

//            setButtonState(startButton, true);
//            setButtonState(cancelButton, false);
        } else if (event == Constants.NuiEvent.EVENT_TRANSCRIBER_COMPLETE) {
//            setButtonState(startButton, true);
//            setButtonState(cancelButton, false);
        }
    }

    //?????????NativeNui???start????????????????????????????????????????????????????????????buffer????????????????????????????????????
    //????????????????????????????????????????????????????????????return?????????????????????????????????????????????<=0??????????????????
    @Override
    public int onNuiNeedAudioData(byte[] buffer, int len) {
        int ret = 0;
        if (mAudioRecorder.getState() != AudioRecord.STATE_INITIALIZED) {
            Log.e(TAG, "audio recorder not init");
            return -1;
        }
        ret = mAudioRecorder.read(buffer, 0, len);
        return ret;
    }

    //??????????????????????????????????????????
    @Override
    public void onNuiAudioStateChanged(Constants.AudioState state) {
        Log.i(TAG, "onNuiAudioStateChanged");
        if (state == Constants.AudioState.STATE_OPEN) {
            Log.i(TAG, "audio recorder start");
            mAudioRecorder.startRecording();
            Log.i(TAG, "audio recorder start done");
        } else if (state == Constants.AudioState.STATE_CLOSE) {
            Log.i(TAG, "audio recorder close");
            mAudioRecorder.release();
        } else if (state == Constants.AudioState.STATE_PAUSE) {
            Log.i(TAG, "audio recorder pause");
            mAudioRecorder.stop();
        }
    }

    @Override
    public void onNuiAudioRMSChanged(float val) {
        Log.i(TAG, "onNuiAudioRMSChanged vol " + val);
    }

    @Override
    public void onNuiVprEventCallback(Constants.NuiVprEvent event) {
        Log.i(TAG, "onNuiVprEventCallback event " + event);
    }

    @Override
    public void initDataObserver() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_demo;
    }
}



