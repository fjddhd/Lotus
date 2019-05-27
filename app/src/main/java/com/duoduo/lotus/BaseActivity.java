package com.duoduo.lotus;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.duoduo.lotus.Connection.HttpCon;
import android.util.Base64;


import java.util.Date;

public class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void InitToolbar(int toolbarId, String toolbarTitle, boolean needBack){
        mToolbar = findViewById(toolbarId);//视活动而定
        mToolbar.setTitle(toolbarTitle);//customize the title,个性化设置title
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(needBack);
        if (needBack==true){
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();//关闭当前活动
                }
            });
        }
    }
    //获取系统时间
    public String getCurrenttime(){
        return (new Date().toLocaleString());
    }
    public String getSerialNumber(){
        String SerialNumber = android.os.Build.SERIAL;
        return SerialNumber;
    }

    public void sendXG(String content, String title, Handler handler){
        String messageContent="\"content\":"+"\""+content+"\"";
        String messageTitle="\"title\":"+"\""+title+"\"";
        StringBuilder sb=new StringBuilder();
        sb.append(messageContent);sb.append(",");sb.append(messageTitle);
        String url="https://openapi.xg.qq.com/v3/push/app" +
                "?audience_type=all&platform=all&" +
                "message={"+sb.toString()+"}" +
                "&message_type=1";
        HttpCon.SendXGPush("",url,handler,HttpCon.SENDXG_SUCCESS,HttpCon.SENDXG_FAILED,
                "推送发送成功","推送发送失败","Authorization",
                "Basic "+getAuthorization(APPID+":"+SECRETKEY));
    }

    public static String APPID="2100334188";
    public static String SECRETKEY="90309f55f3ca5099538592404fb64107";

    public String getAuthorization(String s){
        String strBase64 = Base64.encodeToString(s.getBytes(), Base64.DEFAULT);
        System.out.println("Authorization: "+strBase64);
            return strBase64;

    }
}
