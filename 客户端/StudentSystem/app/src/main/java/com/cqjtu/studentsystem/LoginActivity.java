package com.cqju.studentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/***
 * 登录界面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edt_uname;
    private EditText edt_pwd;
    private Button btn_login;
    private String switches;
    private boolean busy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_uname=(EditText)findViewById(R.id.edit_account);
        edt_pwd=(EditText)findViewById(R.id.edit_passwd);
        btn_login=(Button)findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        switches=getIntent().getStringExtra("switches");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btn_login:
                if(edt_uname.getText().toString().trim().equals(""))
                {
                    Toast.makeText(LoginActivity.this, "用户名不能为空！",
                            Toast.LENGTH_LONG).show();
                }
                else if(edt_pwd.getText().toString().trim().equals(""))
                {
                    Toast.makeText(LoginActivity.this, "密码不能为空！",
                            Toast.LENGTH_LONG).show();
                }else {
                    login();
                    if(busy){
                        Toast.makeText(LoginActivity.this, "服务器连接失败！",
                                Toast.LENGTH_LONG).show();}
                }
                break;
        }
    }

    private void login() {
        new Thread() {
            @Override
            public void run() {//子线程里
                String url = HttpUtils.BASE_URL + "/Login";
                Map<String, String> params = new HashMap<String, String>();
                String userName = edt_uname.getText().toString().trim();
                String password = edt_pwd.getText().toString().trim();
                params.put("account", userName);
                params.put("pwd", password);
                params.put("switches",switches);
                String result = HttpUtils.getContextByHttp(url, params);
                if("".equals(result.trim())||result==null) {
                    busy = true;
                    return ;
                }
                else
                    busy=false;
                ///返回JSON
                Message msg = new Message();
                msg.what = 0x11;
                Bundle data = new Bundle();
                data.putString("result", result);
                msg.setData(data);
                handler.sendMessage(msg);

            }
            Handler handler=new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if(msg.what==0x11){
                        Bundle data = msg.getData();
                        String key = data.getString("result");//得到json返回的json
                        try {
                            JSONObject json= new JSONObject(key);
                            Integer code = (Integer) json.get("code");
                            System.out.println(code);
                            if (code==1) {
                                Intent intent;
                                if("admin".equals(switches))
                                {
                                 intent=new Intent();
                                 intent.setClass(LoginActivity.this,AdminActivity.class);
                                 intent.putExtra("uname",edt_uname.getText().toString().trim());
                                 startActivity(intent);
                                }else if("student".equals(switches))
                                {
                                    intent=new Intent();
                                    intent.setClass(LoginActivity.this,StudentActivity.class);
                                    intent.putExtra("uname",edt_uname.getText().toString().trim());
                                    startActivity(intent);
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "用户名或密码错误！",
                                        Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
        }.start();
    }
}

