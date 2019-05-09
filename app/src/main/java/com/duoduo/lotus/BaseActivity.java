package com.duoduo.lotus;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void InitToolbar(int toolbarId, String toolbarTitle, boolean needBack){
        mToolbar = findViewById(toolbarId);//视活动而定
        mToolbar.setTitle(toolbarTitle);//customize the title,个性化设置title
        mToolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(needBack);
        if (needBack==true){
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();//关闭当前活动
                }
            });
        }
    }
}
