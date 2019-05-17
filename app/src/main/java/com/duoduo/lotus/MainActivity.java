package com.duoduo.lotus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.duoduo.lotus.Connection.HttpCon;
import com.duoduo.lotus.Utils.Mysp;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends BaseActivity {

    private EditText ed;
    private Button btn_wantSend;
    private TextView tv;
    private ArrayList<Integer> al;
    private EditText ed2;
    private int knockBackdoor;
    private boolean OpenBackdoor;
    private Calendar cal;
    private Button btn_choujiang;
    private Button btn_wantChoujiang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitToolbar(R.id.tool_bar_main, "安安的未来多多", false);
        confXinGe();//配置信鸽推送
        sendOpenMessage();//向服务器发送设备号和app时间

        FVBid();
        knockBackdoor = 0;
        OpenBackdoor = false;

        btn_wantSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String s1 = ed.getText().toString();
                String s2 = ed2.getText().toString();

                //180.76.185.86
                StringBuilder sb = new StringBuilder();
                Mysp mysp = new Mysp(MainActivity.this, "connection");
                sb.append("http://" + mysp.getString("server",
                        view.getContext().getString(R.string.defaultserver)) + "/duoduoanan?s1=");//涉及到第一次启动，从String文件中取默认服务器地址
                sb.append(s1 + "&s2=");
                sb.append(s2);
                //发送时间--测试用
                sb.append("&sendTime=" + (new Date().toLocaleString()));
                HttpCon.createPostString("", sb.toString(), handler);

                System.out.println(getMonyhandDay());

            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (knockBackdoor >= 10) {
                    OpenBackdoor = true;
                }
                knockBackdoor++;
            }
        });
        tv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (OpenBackdoor) {
                    OpenBackdoor = false;
                    startActivity(new Intent(MainActivity.this, BackDoorActivity.class));
                    return false;
                }
                tv.setText("");
                Toast.makeText(MainActivity.this, "哎气气，安安把未来多多重置了呢",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        btn_wantChoujiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder();
                Mysp mysp = new Mysp(MainActivity.this, "connection");
                sb.append("http://" + mysp.getString("server",
                        view.getContext().getString(R.string.defaultserver)) + "/duoduoananwantchoujiang?s1=");
                sb.append(getMonyhandDay());
                HttpCon.createChoujiangAuth("", sb.toString(), handler);
            }
        });

        btn_choujiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //发送抽奖请求到/duoduoananchoujiang
                //handler响应成功必须重置重叠按钮显示
                StringBuilder sb = new StringBuilder();
                Mysp mysp = new Mysp(MainActivity.this, "connection");
                sb.append("http://" + mysp.getString("server",
                        view.getContext().getString(R.string.defaultserver)) + "/duoduoananchoujiang");
                HttpCon.createChoujiang("", sb.toString(), handler);

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        OpenBackdoor = false;
        knockBackdoor = 0;//重置后门
    }

    /**
     * 获取系统时间和设备号并发送，在每次开启主活动的时候发送
     * SENDSERIALTIME_SUCCEED,SENDSERIALTIME_FAILED在app端暂不需要处理
     * */
    public static final int SENDSERIALTIME_SUCCEED = 0;
    public static final int SENDSERIALTIME_FAILED = 1;
    public void sendOpenMessage(){
        StringBuilder sb=new StringBuilder();
        Mysp mysp = new Mysp(MainActivity.this, "connection");
        sb.append("http://" + mysp.getString("server",
                getString(R.string.defaultserver)) + "/serialnumber?serial=");
        sb.append(getSerialNumber());
        sb.append("   发送时间为=== ");
        sb.append(getCurrenttime());
        String url=sb.toString();
        HttpCon.SendMessageMethodGet("",url,handler,SENDSERIALTIME_SUCCEED,SENDSERIALTIME_FAILED,
        "发送设备号和手机时间成功","发送设备号和手机时间失败");
    }


    public static final int PUSH_SUCCEED = 2;
    public static final int PUSH_FAILED = 3;
    public static final int CHECK_AUTH_SUCCESS = 8;
    public static final int CHECK_AUTH_FAILED = 9;
    public static final int CHOUJIANG_SUCCESS = 12;
    public static final int CHOUJIANG_FAILED = 13;
    @SuppressLint("HandlerLeak")
    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PUSH_SUCCEED:
                    String s = msg.obj.toString();
                    if (s.length() > 100) {//返回值长度大于100也表示没成功
                        Toast.makeText(MainActivity.this,
                                getString(R.string.connectfailed), Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (s.equals("哎，暗号号！震动一下惩罚安安安")) {
                        Vibrator vibrator = (Vibrator) getApplication().getSystemService(MainActivity.VIBRATOR_SERVICE);
                        vibrator.vibrate(3000);
                    }
                    tv.setText(s);
                    break;
                case PUSH_FAILED:
                    Toast.makeText(MainActivity.this,
                            getString(R.string.connectfailed), Toast.LENGTH_SHORT).show();
                    break;
                case CHECK_AUTH_SUCCESS:
                    String s_check = msg.obj.toString();
                    Toast.makeText(MainActivity.this, s_check, Toast.LENGTH_SHORT);

                    if (s_check.equals("没有奖奖抽呢")) {
                        Toast.makeText(MainActivity.this,
                                "现在还不给抽奖呐安安安，快去向现在多多申请嘛", Toast.LENGTH_SHORT).show();
                    } else {
                        //允许抽奖，重叠按钮切换为抽奖按钮
                        btn_wantChoujiang.setVisibility(View.GONE);
                        btn_choujiang.setVisibility(View.VISIBLE);

                        tv.setText(Html.fromHtml("本次抽奖可以抽到的奖励是： <font color='#ff0000'>" + s_check + "</font>"));
                    }
                    break;
                case CHECK_AUTH_FAILED:
                    Toast.makeText(MainActivity.this,
                            getString(R.string.connectfailed), Toast.LENGTH_SHORT).show();
                    break;
                case CHOUJIANG_SUCCESS:
                    tv.setText("抽奖结果是： " + msg.obj.toString() + "  截图给多多才有效哦安安安~");
                    //重叠按钮重置
                    btn_choujiang.setVisibility(View.GONE);
                    btn_wantChoujiang.setVisibility(View.VISIBLE);
                    break;
                case CHOUJIANG_FAILED:
                    Toast.makeText(MainActivity.this,
                            getString(R.string.connectfailed), Toast.LENGTH_SHORT).show();
                    break;

            }

        }
    };

    public Boolean valify(EditText ed, int limit) {
        String s = ed.getText().toString();
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                Toast.makeText(MainActivity.this, "气气，安安不看字字的！",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        int num = Integer.parseInt(s);
        if (num > limit || num <= 0) {
            Toast.makeText(MainActivity.this, "气气，安安不看字字的！",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void FVBid() {
        ed = findViewById(R.id.ed_duoduo);
        ed2 = findViewById(R.id.ed_anan);
        btn_wantSend = findViewById(R.id.btn_main);
        tv = findViewById(R.id.tv_main);
        btn_wantChoujiang = findViewById(R.id.btn_wantchoujiang);
        btn_choujiang = findViewById(R.id.btn_choujiang);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);//启用菜单
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_setting:
                Intent i = new Intent(MainActivity.this, SettingPage.class);
                startActivity(i);
                break;
            case R.id.menu_back:
                Intent j = new Intent(MainActivity.this, SecurityPage.class);
                startActivity(j);
                break;
            case R.id.menu_tenchou:
                Intent k = new Intent(MainActivity.this,TenChoujiangActivity.class);
                startActivity(k);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public String getMonyhandDay() {
        cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
        String day = String.valueOf(cal.get(Calendar.DATE));
        month = month.length() < 2 ? "0" + month : month;
        day = day.length() < 2 ? "0" + day : day;

        return month + day;
    }

    /**
     * 信鸽配置
     * */
    public void confXinGe(){
        //开启debug日志数据
        XGPushConfig.enableDebug(this,true);
        //开启厂商通道初始化代码
        XGPushConfig.enableOtherPush(getApplicationContext(), true);
        XGPushConfig.setHuaweiDebug(true);
        XGPushConfig.setMiPushAppId(getApplicationContext(), "APPID");
        XGPushConfig.setMiPushAppKey(getApplicationContext(), "APPKEY");
        XGPushConfig.setMzPushAppId(this, "APPID");
        XGPushConfig.setMzPushAppKey(this, "APPKEY");
        //Token注册
        XGPushManager.registerPush(this, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                //token在设备卸载重装的时候有可能会变
                Log.d("TPush", "注册成功，设备token为：" + data);
            }
            @Override
            public void onFail(Object data, int errCode, String msg) {
                Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);
            }
        });

        //设置账号
        XGPushManager.bindAccount(getApplicationContext(), "XINGE");
        //设置标签
        XGPushManager.setTag(this,"XINGE");

        //小米推送通道
        XGPushConfig.setMiPushAppId(getApplicationContext(), "2882303761518006879");
        XGPushConfig.setMiPushAppKey(getApplicationContext(), "5821800612879");
        //打开第三方推送
        XGPushConfig.enableOtherPush(getApplicationContext(), true);
    }
}
