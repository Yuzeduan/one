package com.yuzeduan.util;


import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import static com.yuzeduan.util.OneApplication.requestQueue;

public class VolleyUtil {
    public static void getDataByVolley(String url, final HttpCallbackListener listener){
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                listener.onFinish(s);
            }
        }, null);
        requestQueue.add(request);
    }
}
