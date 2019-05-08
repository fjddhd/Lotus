package com.duoduo.lotus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private EditText ed;
    private Button btn;
    private TextView tv;
    private ArrayList<Integer> al;
    private EditText ed2;
    private int knockBackdoor;
    private boolean OpenBackdoor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FVBid();
        knockBackdoor = 0;
        OpenBackdoor=false;

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String s1=ed.getText().toString();
                String s2=ed2.getText().toString();

                //180.76.185.86
                StringBuilder sb=new StringBuilder();
                sb.append("http://180.76.185.86/duoduoanan?s1=");
                sb.append(s1+"&s2=");
                sb.append(s2);
                HttpCon.createPostString("",sb.toString(),handler);

            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (knockBackdoor>=5){
                    OpenBackdoor=true;
                }
                knockBackdoor++;
            }
        });
        tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (OpenBackdoor){
                    OpenBackdoor=false;
                    startActivity(new Intent(MainActivity.this,BackDoorActivity.class));
                    return false;
                }
                tv.setText("");
                Toast.makeText(MainActivity.this,"哎气气，安安把未来多多重置了呢",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        OpenBackdoor=false;
        knockBackdoor=0;//重置后门
    }

    public static final int PUSH_SUCCEED = 2;
    public static final int PUSH_FAILED = 3;
    @SuppressLint("HandlerLeak")
    protected Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case PUSH_SUCCEED:
                    String s= msg.obj.toString();
                    if (s.length()>100){
                        Toast.makeText(MainActivity.this,"未来多多现在不在呐，安安安",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (s.equals("哎，暗号号！震动一下惩罚安安安")){
                        Vibrator vibrator = (Vibrator)getApplication().getSystemService(MainActivity.VIBRATOR_SERVICE);
                        vibrator.vibrate(3000);
                    }
                    tv.setText(s);
                    break;
                case PUSH_FAILED:
                    Toast.makeText(MainActivity.this,"未来多多现在不在呐，安安安",Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
    public Boolean valify(EditText ed,int limit){
        String s=ed.getText().toString();
        for (int i=0;i<s.length();++i){
            if (s.charAt(i)<'0' || s.charAt(i)>'9'){
                Toast.makeText(MainActivity.this,"气气，安安不看字字的！",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        int num=Integer.parseInt(s);
        if (num>limit ||num<=0){
            Toast.makeText(MainActivity.this,"气气，安安不看字字的！",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public void FVBid(){
        ed = findViewById(R.id.ed_duoduo);
        ed2 = findViewById(R.id.ed_anan);
        btn = findViewById(R.id.btn_main);
        tv = findViewById(R.id.tv_main);
    }
}
