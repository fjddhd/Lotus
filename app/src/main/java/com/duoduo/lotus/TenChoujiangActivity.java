package com.duoduo.lotus;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.duoduo.lotus.Connection.HttpCon;
import com.duoduo.lotus.Utils.Mysp;

import java.util.ArrayList;

public class TenChoujiangActivity extends BaseActivity {

    private Button btn_chou;
    private ListView ten_lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenchoujiang_layout);
        InitToolbar(R.id.tool_bar_tenchou,"超级刚需十连抽",true);
        FVBid();
        final ArrayList<String> jiangchi=createJiangChi(2,10,0);
        btn_chou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] results=new String[10];
                for (int i=0;i<10;++i){
                    results[i]=jiangchi.get((int) (Math.random()*jiangchi.size()));
                }
                initialLV(results);
            }
        });

    }

    public void FVBid(){
        btn_chou = findViewById(R.id.ten_btnchou);
        ten_lv = findViewById(R.id.ten_lv);
    }
    public void initialLV(String[] ss){
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(
                TenChoujiangActivity.this,android.R.layout.simple_list_item_1, ss){
            @SuppressLint("ResourceAsColor")
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {//让内部tv文本居中
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setGravity(Gravity.CENTER);
                String s=textView.getText().toString();
                int i;
                for (i=0;s.charAt(i)=='★';++i){
                    //计数★数量保存到i
                }

                System.out.println(textView.getText()+"   "+i);
                textView.setTextColor(textView.getContext().getColor(R.color.black));//坑：不加这个的话每次第一个item会继承上一组listview
                if (i==4){
                    textView.setTextColor(textView.getContext().getColor(R.color.fourstar));//坑：不能直接写R.color
                }else if (i==5){
                    textView.setTextColor(textView.getContext().getColor(R.color.fivestar));
                }else if (i==3){
                    textView.setTextColor(textView.getContext().getColor(R.color.threestar));
                }
                return textView;
            }
        };
        ten_lv.setAdapter(adapter);//填充lv

        //填充后动态修改颜色


        ten_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }
    public ArrayList<String> createJiangChi(int star1,int star2,int star3){
        ArrayList<String> al=new ArrayList<>();
        al.add("★☆ 两行泪多多");
        al.add("★★★☆ 河粉多多");
        al.add("★★☆ 酒酒多多");
        al.add("★★★ 觉觉多多");
        al.add("★★★★★ 超级多多");
        al.add("没星星-班班多多");
        al.add("★★★★ 粤语多多");
        al.add("★★★★☆ 乖多多");
        al.add("★ 早起多多");
        al.add("★★☆ 早起超级多多");
        al.add("☆ 困饿多");
        al.add("★★★★ 待机多多");
        al.add("★★★★★ 乖乖多多");
        al.add("★★★★☆ 螃蟹多多");
        al.add("★★★ 奇怪多多");
        al.add("★★★★★ 见安安多多");
        al.add("★★★ 澡澡多多");
        al.add("★★★★ 抽奖多多");
        al.add("★★★★ 刚需多多");
        al.add("☆ 不开心多多");
        al.add("★★★★★ 有安安多多");
        al.add("★★★★☆ 学粤语多多");
        al.add("★★★★☆ 酸菜鱼多多");
        al.add("★★★☆ 拉面多多");
        al.add("★★★★★ 馄饨多多");
        al.add("★★★ 甲甲多多");
        al.add("★★★★☆ 一起游戏多多");
        al.add("★★★★★ 幸运多多");
        al.add("★★★★★ 在一起多多");
        al.add("★★★★ 奇妙多多");
        for (int i=0;i<star1;++i){
            al.add("☆ 777");
            al.add("☆ 不开心多多");
            al.add("☆ 困饿多");
            al.add("★ 没有坏多多");
        }
        for (int i=0;i<star2;++i){
            al.add("★☆ 超级欢乐多");
            al.add("★ 早起多多");
        }
        for (int i=0;i<star3;++i){

        }
        return al;


    }




}
