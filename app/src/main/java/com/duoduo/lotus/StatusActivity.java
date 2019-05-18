package com.duoduo.lotus;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.duoduo.lotus.Connection.HttpCon;
import com.duoduo.lotus.Utils.Mysp;

import org.w3c.dom.Text;

import me.zhouzhuo.zzhorizontalprogressbar.ZzHorizontalProgressBar;

public class StatusActivity extends BaseActivity {

    private Button btn_getstatus;
    private ZzHorizontalProgressBar progressbar_mind;
    private TextView tv_describetion;
    private ZzHorizontalProgressBar progressBar_hara;
    private ZzHorizontalProgressBar progressBar_nemuru;
    private TextView tv_statusChangeTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statuslayout);
        InitToolbar(R.id.tool_bar_status,"多多当前状态",true);
        FVBid();

        btn_getstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //由/getstatus处理，用来获取状态可描述
//                String desc=;
//                String mind=;
//                String hara=;
//                String nemuru=;
                Mysp sp=new Mysp(StatusActivity.this,"connection");
                String url="http://"+sp.getString("server",
                        view.getContext().getString(R.string.defaultserver))+
                        "/getstatus";
                HttpCon.SendMessageMethodGet("",url,handler,GETSTATUS_SUCCESS,
                        GETSTATUS_FAILED,"获取状态成功",
                        "获取状态失败"
                        );

            }
        });


    }
    public void FVBid(){
        btn_getstatus = findViewById(R.id.status_btn_getstatus);
        progressbar_mind = findViewById(R.id.status_pb_mind);
        progressBar_hara = findViewById(R.id.status_pb_hara);
        progressBar_nemuru = findViewById(R.id.status_pb_nemuru);
        tv_describetion = findViewById(R.id.status_tv_describetion);//显示设置的特定描述
        tv_statusChangeTime = findViewById(R.id.status_tv_statuschangetime);//显示上次更新时间
    }

    public static final int GETSTATUS_SUCCESS = 18;
    public static final int GETSTATUS_FAILED = 19;
    @SuppressLint("HandlerLeak")
    protected Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GETSTATUS_SUCCESS:
                    //获取到的信息用来刷新控件
                    String s=msg.obj.toString();
                    String[] splitted_s = s.split("=");//注：用 = 分隔
                    if (splitted_s.length<5 || splitted_s.equals("no")){
                        Toast.makeText(StatusActivity.this,"哎，状态获取失败了呢，未来多多的家坏掉了呢"
                                ,Toast.LENGTH_SHORT).show();
                    }
                    //打印验证接收结果
                    int i=0;
                    for (String str:splitted_s){
                        System.out.println(str+"  ---------  "+i);
                        i++;
                    }


                    if (splitted_s[0].length()==0) {
                        tv_describetion.setVisibility(View.GONE);
                    }else {
                        tv_describetion.setVisibility(View.VISIBLE);
                        tv_describetion.setText(splitted_s[0]);
                    }
                    try {
                        if (Integer.parseInt(splitted_s[1])>=0 && Integer.parseInt(splitted_s[1])<=100) {
                            setProgressByNum(progressbar_mind,Integer.parseInt(splitted_s[1]));
                        }
                        if (Integer.parseInt(splitted_s[2])>=0 && Integer.parseInt(splitted_s[2])<=100) {
                            setProgressByNum(progressBar_hara,Integer.parseInt(splitted_s[2]));
                        }
                        if (Integer.parseInt(splitted_s[3])>=0 && Integer.parseInt(splitted_s[3])<=100) {
                            setProgressByNum(progressBar_nemuru,Integer.parseInt(splitted_s[3]));
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        Toast.makeText(StatusActivity.this,
                                "哎，状态获取的格式不正确呢"
                                ,Toast.LENGTH_SHORT).show();
                    }
                    tv_statusChangeTime.setText("最近的多多状态更新于："+splitted_s[4]);

                    break;
                case GETSTATUS_FAILED:
                    Toast.makeText(StatusActivity.this,"哎，状态获取失败了呢，未来多多的家坏掉了呢"
                    ,Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    public void setProgressByNum(ZzHorizontalProgressBar pb,int num){
        pb.setProgress(num);
        if (num>=80){
            pb.setProgressColor(getColor(R.color.statusGreat));
        }
        else if (num>60){
            pb.setProgressColor(getColor(R.color.statusWell));
        }
        else if (num>40){
            pb.setProgressColor(getColor(R.color.statusNormal));
        }
        else {
            pb.setProgressColor(getColor(R.color.statusBad));
        }
    }

}