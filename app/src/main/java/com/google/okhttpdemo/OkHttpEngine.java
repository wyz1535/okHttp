package com.google.okhttpdemo;

import android.content.Context;
import android.os.Handler;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/9/28.
 */
public class OkHttpEngine {


    private static OkHttpEngine mInstance;
    private final OkHttpClient mOkHttpClient;
    private final Handler mHandler;

    public static OkHttpEngine getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpEngine.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpEngine();
                }
            }
        }
        return mInstance;
    }

    public OkHttpEngine() {
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(1500, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(2000, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(2000, TimeUnit.SECONDS);
        mHandler = new Handler();
    }

    public OkHttpEngine getCache(Context context) {
        File cacheDir = context.getExternalCacheDir();
        int cachaSize = 10 * 1024 * 1024;
        mOkHttpClient.setCache(new Cache(cacheDir.getAbsoluteFile(), cachaSize));
        return mInstance;
    }

    public void getAsynHttp(String url, ResultCallback callback) {
        Request request = new Request.Builder().get().url(url).build();
        Call call = mOkHttpClient.newCall(request);
        dealResult(call, callback);
    }

    private void dealResult(Call call, final ResultCallback callback) {
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(final Response response) throws IOException {
//                sendSuccessCallback(response, callback);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onResponse(response);
                        }
                    }
                });
            }

        });
    }


    private void sendSuccessCallback(final Response response, final ResultCallback callback) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onResponse(response);
                }
            }
        });
    }
}
