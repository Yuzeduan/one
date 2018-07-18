package com.yuzeduan.model;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.yuzeduan.bean.Comment;
import com.yuzeduan.util.ParseJSONUtil;

import java.util.List;

import static com.yuzeduan.util.OneApplication.requestQueue;

public class CommentModel {
    public void queryCommentData(String url, final CallbackListener listener){
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                List<Comment> list = ParseJSONUtil.parseComment(s);
                listener.onStringFinish(list);
            }
        }, null);
        requestQueue.add(request);
    }
}
