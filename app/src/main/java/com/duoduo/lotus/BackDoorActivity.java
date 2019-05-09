package com.duoduo.lotus;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.duoduo.lotus.Connection.HttpCon;
import com.duoduo.lotus.Utils.Mysp;

public class BackDoorActivity extends BaseActivity {

    private Button btn;
    private ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backdoor_layout);
        InitToolbar(R.id.tool_bar_back,"多多的便捷后门",true);
        FVBid();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //180.76.185.86
                StringBuilder sb=new StringBuilder();
                Mysp mysp=new Mysp(BackDoorActivity.this,"connection");
                sb.append("http://"+mysp.getString("server")+"/duoduoananget");
                HttpCon.createGetString("",sb.toString(),handler);

            }
        });

    }
    private void FVBid(){
        lv = findViewById(R.id.back_lv);//注意，lv外面一定不要嵌套scrollview
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
                    System.out.println("backdoor：===拿到的所有信息： "+s);
                    String[] lv_data=s.split("\n");//以换行符作为分隔符
                    System.out.println("backdoor：===拿到的所有信息的数量： "+lv_data.length);
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                            BackDoorActivity.this,android.R.layout.simple_list_item_1, lv_data);
                    lv.setAdapter(adapter);
                    break;
                case GET_FAILED:
                    Toast.makeText(BackDoorActivity.this,"没成功获取到呢，也许网络连接出了问题",Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
}
