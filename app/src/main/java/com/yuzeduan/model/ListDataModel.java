package com.yuzeduan.model;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.yuzeduan.bean.Constant;
import com.yuzeduan.bean.InsetId;
import com.yuzeduan.util.ParseJSONUtil;

import java.util.Iterator;
import java.util.List;

import static com.yuzeduan.bean.Constant.INSET;
import static com.yuzeduan.bean.Constant.MUSIC;
import static com.yuzeduan.bean.Constant.READING;
import static com.yuzeduan.util.OneApplication.requestQueue;

public class ListDataModel {
    private int total = 0;  //记录请求的插画个数

    public void queryDataFromServer(String url, final int flag, final CallbackListener listener){
        StringRequest mStrReq = null;
        if(flag == INSET) {
            mStrReq = new StringRequest(url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    List<InsetId> idList = ParseJSONUtil.parseInsetId(s);
                    Iterator<InsetId> iterator = idList.iterator();
                    InsetId insetId;
                    while(iterator.hasNext()){
                        insetId = iterator.next();
                        String id = insetId.getmInsetId();
                        String insetUrl = Constant.createInsetUrl(id);
                        StringRequest insetRequest = new StringRequest(insetUrl, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                ParseJSONUtil.parseInset(s);
                                total++;
                                if(total == 10){
                                    if(listener != null) {
                                        listener.onFinish();
                                    }
                                    total = 0;
                                }
                            }
                        }, null);
                        requestQueue.add(insetRequest);
                    }
                }
            }, null);
            requestQueue.add(mStrReq);
        }
        else{
            mStrReq = new StringRequest(url, new Response.Listener<String>(){
                @Override
                public void onResponse(String s) {
                    if(flag == READING){
                        ParseJSONUtil.parseReadingMusicList(s, READING);
                    }
                    else if(flag == MUSIC){
                        ParseJSONUtil.parseReadingMusicList(s, MUSIC);
                    }
                    else {
                        ParseJSONUtil.parseMovieList(s);
                    }
                    if(listener != null) {
                        listener.onFinish();
                    }
                }
            }, null);
            requestQueue.add(mStrReq);
        }
    }
}
