package com.duoduo.lotus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.duoduo.lotus.Utils.Mysp;

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
        if (mysp.getString("server").length()<1){
            ed1.setText("180.76.185.86");//在sp中没取到值就先设置默认server，并存到sp中
            mysp.putString("server","180.76.185.86");//直接保存，防止在onPause（）方法执行前ed内容变化
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveConnectionAddress();
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        saveConnectionAddress();
    }

    public void FVBid(){
        ed1 = findViewById(R.id.setting_ed1);
        btn = findViewById(R.id.setting_btn);
    }
    public void saveConnectionAddress(){//用connection-server这个sp存储服务器地址，从ed1中取值
        Mysp mysp=new Mysp(SettingPage.this,"connection");
        mysp.putString("server",ed1.getText().toString());
        System.out.println("SettingPage=========当前所访问的服务器地址为： "+ed1.getText().toString());
        Toast.makeText(SettingPage.this,"保存地址： "+ed1.getText().toString()+" 成功！",
                Toast.LENGTH_SHORT).show();
    }
}
