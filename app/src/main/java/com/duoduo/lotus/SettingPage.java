package com.duoduo.lotus;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.duoduo.lotus.Connection.HttpCon;
import com.duoduo.lotus.Utils.Mysp;

import java.util.Date;

public class SettingPage extends BaseActivity {

    private EditText ed1;
    private Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        InitToolbar(R.id.tool_bar_setting,"设置页面",true);
        FVBid();
        Mysp mysp=new Mysp(SettingPage.this,"connection");
        if (mysp.getString("server","").length()<1){
            ed1.setText(this.getString(R.string.defaultserver));//在sp中没取到值就先设置默认server，并存到sp中
            mysp.putString("server",this.getString(R.string.defaultserver));//直接保存，防止在onPause（）方法执行前ed内容变化
        }else {
            ed1.setText(mysp.getString("server",""));
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveConnectionAddress(true);
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        saveConnectionAddress(false);
    }

    public void FVBid(){
        ed1 = findViewById(R.id.setting_ed1);
        btn = findViewById(R.id.setting_btn);




        //测试网络连通性---2019/5/14
//        final Button btn1=findViewById(R.id.setting_btn1);
        final Button btn2=findViewById(R.id.setting_btn2);
        final Button btn3=findViewById(R.id.setting_btn3);

//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ed1.setText(btn1.getText().toString());
//                //180.76.185.86
//                StringBuilder sb=new StringBuilder();
//                Mysp mysp=new Mysp(SettingPage.this,"connection");
//                sb.append("http://"+btn1.getText().toString()+"/duoduoanan?s1=");//涉及到第一次启动，从String文件中取默认服务器地址
//                sb.append("测试连通性"+"&s2=");
//                sb.append("145");
//                //发送时间--测试用
//                sb.append("&sendTime="+(new Date().toLocaleString()));
//                HttpCon.createPostString("",sb.toString(),new Handler());
//            }
//        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed1.setText(btn2.getText().toString());
                //180.76.185.86
                StringBuilder sb=new StringBuilder();
                Mysp mysp=new Mysp(SettingPage.this,"connection");
                sb.append("http://"+btn2.getText().toString()+"/duoduoanan?s1=");//涉及到第一次启动，从String文件中取默认服务器地址
                sb.append("测试连通性"+"&s2=");
                sb.append("145");
                //发送时间--测试用
                sb.append("&sendTime="+(new Date().toLocaleString()));

//                HttpCon.createPostString("",sb.toString(),new Handler());

                //5-27连接重构
                HttpCon.SendMessageMethodGet("",sb.toString(),new Handler()
                ,HttpCon.PUSH_SUCCEED,HttpCon.PUSH_FAILED,"测试连通性成功",
                        "测试连通性失败");
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed1.setText(btn3.getText().toString());
                //180.76.185.86
                StringBuilder sb=new StringBuilder();
                Mysp mysp=new Mysp(SettingPage.this,"connection");
                sb.append("http://"+btn3.getText().toString()+"/duoduoanan?s1=");//涉及到第一次启动，从String文件中取默认服务器地址
                sb.append("测试连通性"+"&s2=");
                sb.append("145");
                //发送时间--测试用
                sb.append("&sendTime="+(new Date().toLocaleString()));

//                HttpCon.createPostString("",sb.toString(),new Handler());

                //5-27连接重构
                HttpCon.SendMessageMethodGet("",sb.toString(),new Handler()
                        ,HttpCon.PUSH_SUCCEED,HttpCon.PUSH_FAILED,"测试连通性成功",
                        "测试连通性失败");
            }
        });


    }
    public void saveConnectionAddress(boolean needToast){//用connection-server这个sp存储服务器地址，从ed1中取值
        Mysp mysp=new Mysp(SettingPage.this,"connection");
        mysp.putString("server",ed1.getText().toString());
        System.out.println("SettingPage=========当前所访问的服务器地址为： "+ed1.getText().toString());
        if (needToast) {
            Toast.makeText(SettingPage.this,"保存地址： "+ed1.getText().toString()+" 成功！",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
