package com.xiaoneng.ss.common.utils.aliSpeech;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

public class Auth {
    public static JSONObject getTicket() {
        JSONObject object = new JSONObject();
        object.put("ak_id", "");
        object.put("ak_secret", "");
        object.put("app_key","");
        object.put("token","");
        object.put("device_id",Utils.getDeviceId());
        object.put("sdk_code","");
        object.put("url","");
        return object;
    }

    public static JSONObject getAliYunTicket() {
        JSONObject object = new JSONObject();
        final AccessToken token;
        //From Aliyun 请根据相关文档获取并填入
        String app_key = "fPCaoprH8FaAZwRN";
        String accessKeyId = "LTAI4GAJh132H964sp9hSNDL";
        String accessKeySecret = "9K2Zm19BIVi9Fxkkxh63ixetCvZYkF";


        token = new AccessToken(accessKeyId, accessKeySecret);
        Thread th = new Thread(){
            @Override
            public void run() {
                try {
                    token.apply();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        };
        th.start();
        try {
            th.join(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String token_txt = token.getToken();
        long expired_time = token.getExpireTime();

        object.put("app_key",app_key);
        object.put("token",token_txt);
        object.put("device_id",Utils.getDeviceId());
        return object;
    }
}
