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
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import android.util.Base64;


import java.util.Date;

public class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialXunFei();
//        openXCunFeiSpeech("安安安，快点起床了，今天的刚需是3,15,23,27,36,44哦，超级房房哦，是43号未来多多说的呢");

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

    public void initialXunFei(){
        SpeechUtility.createUtility(getApplication().getApplicationContext(), SpeechConstant.APPID +"=5d195831");
    }
    public void openXCunFeiSpeech(String s){
        //1.创建SpeechSynthesizer对象
        SpeechSynthesizer mTts= SpeechSynthesizer.createSynthesizer(getApplication().getApplicationContext(), new InitListener() {
            @Override
            public void onInit(int i) {
                System.out.println("讯飞开启状态码： "+i);
            }
        });
        //2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "x_xiaomei");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "55");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.startSpeaking(s,mSynListener);

    }
    //合成监听器
    public SynthesizerListener mSynListener = new SynthesizerListener(){
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
            if (error==null){
                System.out.println("播放语音没有错误哦");
            }else {
                System.out.println("播放语音好像有点问题呢");
                System.out.println("播放语音错误码："+error.getErrorCode());
            }
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }

        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {}
        //开始播放
        public void onSpeakBegin() {}
        //暂停播放
        public void onSpeakPaused() {}
        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {}
        //恢复播放回调接口
        public void onSpeakResumed() {}
    };
}
