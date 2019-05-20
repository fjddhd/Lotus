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
    public static final int SENDSERIALTIME_SUCCEED = 0;
    public static final int SENDSERIALTIME_FAILED = 1;
    public static final int PUSH_SUCCEED = 2;
    public static final int PUSH_FAILED = 3;
    public static final int GET_SUCCEED = 4;
    public static final int GET_FAILED = 5;
    public static final int Delete_SUCCEED = 6;
    public static final int Delete_FAILED = 7;
    public static final int CHECK_AUTH_SUCCESS = 8;
    public static final int CHECK_AUTH_FAILED = 9;
    public static final int SET_CHOUJIANG_SUCCESS = 10;
    public static final int SET_CHOUJIANG_FAILED = 11;
    public static final int CHOUJIANG_SUCCESS = 12;
    public static final int CHOUJIANG_FAILED = 13;
    public static final int SENDANHAO_SUCCESS = 14;
    public static final int SENDANHAO_FAILED = 15;
    public static final int SETSTATUS_SUCCESS = 16;
    public static final int SETSTATUS_FAILED = 17;
    public static final int GETSTATUS_SUCCESS = 18;
    public static final int GETSTATUS_FAILED = 19;
    public static final int SETSTATUS_SUCCESS_ANAN = 20;
    public static final int SETSTATUS_FAILED_ANAN = 21;
    public static final int GETSTATUS_SUCCESS_ANAN = 22;
    public static final int GETSTATUS_FAILED_ANAN = 23;
    public static void SendMessageMethodGet(String requestBody, String url, final Handler handler
    , final int SuccessMessage, final int FaliedMessage, final String successLog, final String FailedLog){
        //可以在形参中输入成功失败发送的message和安卓手机打印消息
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
                System.out.println(FailedLog);

                Message message = new Message();
                message.what =FaliedMessage;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println(successLog);
//                System.out.println(response.body().string());


                Message message = new Message();
                message.obj=response.body().string();
                message.what =SuccessMessage;
                handler.sendMessage(message);
            }
        });
    }

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
    public static void createChoujiangAuth(String requestBody, String url, final Handler handler){//post请求需要requestBody
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
                System.out.println("请求抽奖资格失败");

                Message message = new Message();
                message.what =CHECK_AUTH_FAILED;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("请求抽奖资格成功");
//                System.out.println(response.body().string());


                Message message = new Message();
                message.obj=response.body().string();
                message.what =CHECK_AUTH_SUCCESS;
                handler.sendMessage(message);
            }
        });
    }
    public static void createSetChoujiang(String requestBody, String url, final Handler handler){//post请求需要requestBody
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
                System.out.println("设置抽奖资格失败");

                Message message = new Message();
                message.what =SET_CHOUJIANG_FAILED;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("设置抽奖资格成功");
//                System.out.println(response.body().string());


                Message message = new Message();
                message.obj=response.body().string();
                message.what =SET_CHOUJIANG_SUCCESS;
                handler.sendMessage(message);
            }
        });
    }
    public static void createChoujiang(String requestBody, String url, final Handler handler){//post请求需要requestBody
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
                System.out.println("抽奖失败");

                Message message = new Message();
                message.what =CHOUJIANG_FAILED;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("抽奖成功");
//                System.out.println(response.body().string());


                Message message = new Message();
                message.obj=response.body().string();
                message.what =CHOUJIANG_SUCCESS;
                handler.sendMessage(message);
            }
        });
    }
    public static void createSendAnhao(String requestBody, String url, final Handler handler){//post请求需要requestBody
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
                System.out.println("设置暗号失败");

                Message message = new Message();
                message.what =SENDANHAO_FAILED;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("设置暗号成功");
//                System.out.println(response.body().string());


                Message message = new Message();
                message.obj=response.body().string();
                message.what =SENDANHAO_SUCCESS;
                handler.sendMessage(message);
            }
        });
    }
}
