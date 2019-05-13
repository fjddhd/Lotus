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

public class BackDoorActivity extends BaseActivity {

    private Button btn_get;
    private ListView lv;
    private Button btn_deleteAll;
    private Button btn_commitChoujiang;
    private EditText ed_choujiangFail;
    private EditText ed_choujiang;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backdoor_layout);
        InitToolbar(R.id.tool_bar_back,"多多的便捷后门",true);
        FVBid();

        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getServerMessage();

            }
        });

        btn_deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mysp sp=new Mysp(BackDoorActivity.this,"connection");
                String url="http://"+sp.getString("server",
                        view.getContext().getString(R.string.defaultserver))+
                        "/duoduoanandeleteall";
                HttpCon.createDelString("",url,handler);
            }
        });

        btn_commitChoujiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed_choujiangFail.getText().toString().length()<1 ||ed_choujiang.getText().toString().length()<1){
                    Toast.makeText(BackDoorActivity.this,
                            "要填全所有信息才能提交准许抽奖请求",Toast.LENGTH_SHORT).show();
                }else {
                    //发送带参数请求到@server/duoduoanansetchoujiang用于设置抽奖权限
                    String s1=ed_choujiangFail.getText().toString();
                    String s2=ed_choujiang.getText().toString();
                    Mysp sp=new Mysp(BackDoorActivity.this,"connection");
                    String url="http://"+sp.getString("server",
                            view.getContext().getString(R.string.defaultserver))+
                            "/duoduoanansetchoujiang?s1="+s1+"&s2="+s2;
                    HttpCon.createSetChoujiang("",url,handler);

                }
            }
        });



    }
    private void FVBid(){
        lv = findViewById(R.id.back_lv);//注意，lv外面一定不要嵌套scrollview
        btn_get = findViewById(R.id.back_btn_get);
        btn_deleteAll = findViewById(R.id.back_btn_deleteall);
        btn_commitChoujiang = findViewById(R.id.back_btn_commitChoujiang);
        ed_choujiangFail = findViewById(R.id.back_ed_weichoudao);
        ed_choujiang = findViewById(R.id.back_ed_setchoudao);
    }
    public void getServerMessage(){//获取服务器信息并在成功后handler会重新初始化lv并刷新
        //180.76.185.86
        StringBuilder sb=new StringBuilder();
        Mysp mysp=new Mysp(BackDoorActivity.this,"connection");
        sb.append("http://"+mysp.getString("server",this.getString(R.string.defaultserver))+"/duoduoananget");
        HttpCon.createGetString("",sb.toString(),handler);
    }
    public void initialLV(String[] ss){
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                BackDoorActivity.this,android.R.layout.simple_list_item_1, ss);
        lv.setAdapter(adapter);//填充lv
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //发送请求删除
                Mysp sp=new Mysp(BackDoorActivity.this,"connection");
                String url="http://"+sp.getString("server",
                        view.getContext().getString(R.string.defaultserver))+
                        "/duoduoanandeletespecial?s1="+((TextView)view).getText().toString();
                HttpCon.createDelString("",url,handler);
            }
        });
    }

    public static final int GET_SUCCEED = 4;
    public static final int GET_FAILED = 5;
    public static final int Delete_SUCCEED = 6;
    public static final int Delete_FAILED = 7;
    public static final int SET_CHOUJIANG_SUCCESS = 10;
    public static final int SET_CHOUJIANG_FAILED = 11;
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
                    initialLV(lv_data);
                    break;
                case GET_FAILED:
                    Toast.makeText(BackDoorActivity.this,
                            "没成功获取到呢，也许网络连接出了问题",Toast.LENGTH_SHORT).show();
                    break;
                case Delete_FAILED:
                    Toast.makeText(BackDoorActivity.this,
                            "删除失败啦",Toast.LENGTH_SHORT).show();
                    break;


                case Delete_SUCCEED:
                    getServerMessage();//无论怎么删除后都要重新从服务器获取信息并刷新lv
                    break;

                case SET_CHOUJIANG_SUCCESS:
                    Toast.makeText(BackDoorActivity.this,
                            "设置抽奖成功",Toast.LENGTH_SHORT).show();
                    break;
                case SET_CHOUJIANG_FAILED:
                    Toast.makeText(BackDoorActivity.this,
                            "设置抽奖失败",Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
}
