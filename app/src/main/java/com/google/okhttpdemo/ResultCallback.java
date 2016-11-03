package com.google.okhttpdemo;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Created by Administrator on 2016/9/28.
 */
public abstract class ResultCallback {

    public abstract void onError(Request request, Exception e);
    public abstract void onResponse(Response response);
}
