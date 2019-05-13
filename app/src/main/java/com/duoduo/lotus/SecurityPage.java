package com.duoduo.lotus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SecurityPage extends BaseActivity {

    private Button btn_queding;
    private EditText et_duoduoma;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security_layout);
        FVBid();

        btn_queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String duoduoma = et_duoduoma.getText().toString();
                if (duoduoma.equals(view.getContext().getString(R.string.duoduoma))){
                    Intent j = new Intent(SecurityPage.this, BackDoorActivity.class);
                    startActivity(j);
                }else {
                    Toast.makeText(SecurityPage.this,"多多才知道的呐安安安，乖乖嘛",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void FVBid(){
        btn_queding = findViewById(R.id.security_btn);
        et_duoduoma = findViewById(R.id.security_et);
    }
}
