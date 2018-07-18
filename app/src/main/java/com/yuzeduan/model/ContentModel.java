package com.yuzeduan.model;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.yuzeduan.util.ParseJSONUtil;

import static com.yuzeduan.bean.Constant.READING;
import static com.yuzeduan.bean.Constant.MUSIC;
import static com.yuzeduan.util.OneApplication.requestQueue;

public class ContentModel {
    public void queryContentData(String url, final int flag , final CallbackListener listener){
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(flag == READING){
                    ParseJSONUtil.parseReading(s);
                }
                else if(flag == MUSIC){
                    ParseJSONUtil.parseMusic(s);
                }
                else {
                    ParseJSONUtil.parseMovie(s);
                }
                listener.onFinish();
            }
        }, null);
        requestQueue.add(request);
    }
}
