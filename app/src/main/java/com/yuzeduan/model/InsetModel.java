package com.yuzeduan.model;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.yuzeduan.bean.Constant;
import com.yuzeduan.bean.Inset;
import com.yuzeduan.bean.InsetId;
import com.yuzeduan.db.InsetDao;
import com.yuzeduan.util.HttpCallbackListener;
import com.yuzeduan.util.ParseJSONUtil;
import com.yuzeduan.util.VolleyUtil;
import static com.yuzeduan.util.OneApplication.requestQueue;
import static com.yuzeduan.bean.Constant.REFRESH_DATA;

import java.util.Iterator;
import java.util.List;

public class InsetModel {
    private int total = 0;

    public void getInsetListData(String url, InsetCallback callback){
        InsetDao insetDao = new InsetDao();
        List<Inset> insetList = insetDao.findInset();
        // 判断数据库中是否有缓存,如果有,直接从数据库获取并展示,若无,则从服务器中获取
        if(insetList != null){
            callback.onFinish(insetList);
        }else{
            queryInsetListData(url, 0, callback);
        }
    }

    public void queryInsetListData(final String url, final int flag, final InsetCallback callback){
        VolleyUtil.getDataByVolley(url, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                List<InsetId> idList = ParseJSONUtil.parseInsetId(response);
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
                                if(callback != null) {
                                    if(flag == REFRESH_DATA){
                                        callback.onRefresh();
                                    }else{
                                        getInsetListData(url, callback);
                                    }
                                }
                                total = 0;
                            }
                        }
                    }, null);
                    requestQueue.add(insetRequest);
                }
            }
        });
    }
}
