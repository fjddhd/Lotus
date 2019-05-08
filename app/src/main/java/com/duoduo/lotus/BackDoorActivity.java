package com.duoduo.lotus;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BackDoorActivity extends AppCompatActivity {

    private TextView tv;
    private Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backdoor_layout);
        FVBid();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //180.76.185.86
                StringBuilder sb=new StringBuilder();
                sb.append("http://180.76.185.86/duoduoananget");
                HttpCon.createGetString("",sb.toString(),handler);

            }
        });

    }
    private void FVBid(){
        tv = findViewById(R.id.back_tv);
        btn = findViewById(R.id.back_btn);
    }

    public static final int GET_SUCCEED = 4;
    public static final int GET_FAILED = 5;
    protected Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GET_SUCCEED:
                    String s= msg.obj.toString();
                    tv.setText(s);
                    break;
                case GET_FAILED:
                    Toast.makeText(BackDoorActivity.this,"没成功获取到呢，也许网络连接出了问题",Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
}
