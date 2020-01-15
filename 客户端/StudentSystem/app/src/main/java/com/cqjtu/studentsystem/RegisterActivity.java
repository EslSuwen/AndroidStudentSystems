package com.cqju.studentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * 注册界面
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edit_regcode;
    private EditText edit_regaccount;
    private EditText edit_regpwd;
    private EditText edit_regpwd1;
    private Button btn_registerok;
    private final String REGISTERCODE="A";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edit_regcode=(EditText)findViewById(R.id.edit_registercode);
        edit_regaccount=(EditText)findViewById(R.id.edit_regaccount);
        edit_regpwd=(EditText)findViewById(R.id.edit_regpasswd);
        edit_regpwd1=(EditText)findViewById(R.id.edit_regpasswd1);
        btn_registerok=(Button)findViewById(R.id.btn_reg);
        btn_registerok.setOnClickListener(this);

        edit_regpwd.setRawInputType(Configuration.KEYBOARD_QWERTY);
        edit_regpwd1.setRawInputType(Configuration.KEYBOARD_QWERTY);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case  R.id.btn_reg:
                if(!REGISTERCODE.equals(edit_regcode.getText().toString().trim()))
                {
                    Toast.makeText(RegisterActivity.this,"注册码输入错误",Toast.LENGTH_LONG).show();
                }
                else if("".equals(edit_regaccount.getText().toString().trim()) || "".equals(edit_regpwd.getText().toString().trim()))
                {
                    Toast.makeText(RegisterActivity.this,"账号和密码不能为空",Toast.LENGTH_LONG).show();
                }else if(!edit_regpwd.getText().toString().trim().equals(edit_regpwd1.getText().toString().trim()))
                {
                    Toast.makeText(RegisterActivity.this,"两次输入的密码不匹配",Toast.LENGTH_LONG).show();
                }else {
                    String reGex="^\\d{6,6}$";
                    Pattern pattern=Pattern.compile(reGex);
                    Matcher matcher=pattern.matcher(edit_regpwd.getText().toString().trim());
                    if(!matcher.matches())
                    {
                        Toast.makeText(RegisterActivity.this,"密码只能是6位数字",Toast.LENGTH_LONG).show();
                    }else {
                        register();
                    }
                }
                break;

        }
    }
    private void register()
    {
        final String uname=edit_regaccount.getText().toString().trim();
        final String pwd=edit_regpwd.getText().toString().trim();
        new Thread(){
            @Override
            public void run() {
                boolean flag=false;
                String url = HttpUtils.BASE_URL + "/register";
                Map<String, String> params = new HashMap<String, String>();
                params.put("uname", uname);
                params.put("pwd", pwd);
                String result = HttpUtils.getContextByHttp(url, params);//获取服务端返回的数据
                if("".equals(result.trim())||result==null) {
                    flag=false;
                }else {
                    try {
                        JSONObject json = new JSONObject(result);
                        Integer code = (Integer) json.get("code");
                        if (code != 0)
                            flag = true;
                        else if (code == 0)
                            flag = false;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Message msg=new Message();
                msg.what=0x12;
                Bundle bundle=new Bundle();
                bundle.putBoolean("flag",flag);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
            Handler handler=new Handler(){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    boolean regflag;
                    if(msg.what==0x12)
                    {
                        Bundle bundle=msg.getData();
                        regflag=bundle.getBoolean("flag");
                        if (regflag) {
                            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            };
        }.start();
        return ;
    }
}
