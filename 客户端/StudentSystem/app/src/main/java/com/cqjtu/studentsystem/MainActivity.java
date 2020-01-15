package com.cqju.studentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/***
 * 主界面
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_admin;
    private Button btn_stu;
    private Button btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_admin=(Button)findViewById(R.id.btn_admin);
        btn_stu=(Button)findViewById(R.id.btn_stu);
        btn_register=(Button)findViewById(R.id.btn_register);
        btn_stu.setOnClickListener(this);
        btn_admin.setOnClickListener(this);
        btn_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId())
        {
            case R.id.btn_admin:
                intent=new Intent();
                intent.setClass(this, LoginActivity.class);
                intent.putExtra("switches","admin");
                startActivity(intent);
                break;
            case R.id.btn_stu:
                intent=new Intent();
                intent.setClass(this, LoginActivity.class);
                intent.putExtra("switches","student");
                startActivity(intent);
                break;
            case R.id.btn_register:
                intent=new Intent();
                intent.setClass(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }

    }
}
