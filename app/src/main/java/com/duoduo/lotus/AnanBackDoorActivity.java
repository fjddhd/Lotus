package com.duoduo.lotus;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.duoduo.lotus.Connection.HttpCon;
import com.duoduo.lotus.Utils.Mysp;

public class AnanBackDoorActivity extends BaseActivity {

    private EditText ed_desc;
    private EditText ed_setmind;
    private EditText ed_sethara;
    private EditText ed_setnemuru;
    private Button btn_sendananstates;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anan_backdoor_layout);
        InitToolbar(R.id.tool_bar_back4anan,"安安的可操作页面",true);
        FVBid();

        //发送安安状态
        btn_sendananstates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mysp sp=new Mysp(AnanBackDoorActivity.this,"connection");
                String url="http://"+sp.getString("server",
                        view.getContext().getString(R.string.defaultserver))+
                        "/setstatusanan?desc="+ed_desc.getText().toString()
                        +"&mind="+ed_setmind.getText().toString()+
                        "&hara="+ed_sethara.getText().toString()+
                        "&nemuru="+ed_setnemuru.getText().toString();
                HttpCon.SendMessageMethodGet("",url,handler,HttpCon.SETSTATUS_SUCCESS_ANAN
                        ,HttpCon.SETSTATUS_FAILED_ANAN
                        ,"设置状态成功","设置状态失败");
            }
        });

    }
    private void FVBid(){
        ed_desc = findViewById(R.id.back_ed_setstatusdesc_anan);
        ed_setmind = findViewById(R.id.back_ed_setmind_anan);
        ed_sethara = findViewById(R.id.back_ed_sethara_anan);
        ed_setnemuru = findViewById(R.id.back_ed_setnemuru_anan);
        btn_sendananstates = findViewById(R.id.back_btn_sendstatus_anan);
    }

    protected Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case HttpCon.SETSTATUS_SUCCESS_ANAN:
                    Toast.makeText(AnanBackDoorActivity.this,
                            "设置安安状态成功",Toast.LENGTH_SHORT).show();
                    break;
                case HttpCon.SETSTATUS_FAILED_ANAN:
                    Toast.makeText(AnanBackDoorActivity.this,
                            getString(R.string.connectfailed),Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
}
