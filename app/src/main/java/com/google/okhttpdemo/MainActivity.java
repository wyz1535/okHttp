package com.google.okhttpdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tv = (TextView) findViewById(R.id.tv);

//        getOkhttp();


            Bundle bundle = new Bundle();
        OkHttpEngine.getInstance().getCache(this);
        OkHttpEngine.getInstance().getAsynHttp("http://www.baidu.com", new ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {
                String str = response.networkResponse().toString();
                Toast.makeText(MainActivity.this,str,Toast.LENGTH_SHORT).show();
                tv.setText(str);
            }
        });

//        MediaPlayer mediaPlayer = new MediaPlayer();
//        MediaPlayer.create(this,R.)
//        mediaPlayer.start();
//        mediaPlayer.stop();
    }

    public void getOkhttp() {
        synchronized (this) {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().get().url("http://www.baidu.com").build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    String str = response.body().string();
                    Log.e("str", "str" + str);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "请求成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}
