package com.duoduo.lotus.Connection;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class HttpCon {
    public static final int PUSH_SUCCEED = 2;
    public static final int PUSH_FAILED = 3;
    public static final int GET_SUCCEED = 4;
    public static final int GET_FAILED = 5;
    public static final int Delete_SUCCEED = 6;
    public static final int Delete_FAILED = 7;
    public static void createPostString(String requestBody, String url, final Handler handler){//post请求需要requestBody
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
                System.out.println("上传失败");

                Message message = new Message();
                message.what =PUSH_FAILED;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("上传成功");
//                System.out.println(response.body().string());


                Message message = new Message();
                message.obj=response.body().string();
                message.what =PUSH_SUCCEED;
                handler.sendMessage(message);
            }
        });
    }
    public static void createGetString(String requestBody, String url, final Handler handler){//post请求需要requestBody
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
                System.out.println("获取失败");

                Message message = new Message();
                message.what =GET_FAILED;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("获取成功");
//                System.out.println(response.body().string());


                Message message = new Message();
                message.obj=response.body().string();
                message.what =GET_SUCCEED;
                handler.sendMessage(message);
            }
        });
    }
    public static void createDelString(String requestBody, String url, final Handler handler){//post请求需要requestBody
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: " + e.getMessage());
                System.out.println("删除失败");

                Message message = new Message();
                message.what =Delete_FAILED;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("删除成功");
//                System.out.println(response.body().string());


                Message message = new Message();
                message.obj=response.body().string();
                message.what =Delete_SUCCEED;
                handler.sendMessage(message);
            }
        });
    }
}
