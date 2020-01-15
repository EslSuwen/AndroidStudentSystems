package com.cqju.studentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 学生登录后的界面
 */
public class StudentActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_stutitle;
    private TextView tv_info;
    private Button btn_edit;
    private String uname;
    public static Student s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        s=new Student();
        tv_stutitle=(TextView)findViewById(R.id.tv_stutitle);
        tv_info=(TextView)findViewById(R.id.tv_stu);
        btn_edit=(Button)findViewById(R.id.btn_stuedit);
        uname=getIntent().getExtras().getString("uname");

        btn_edit.setOnClickListener(this);
        tv_stutitle.setText("个人信息");
        showinfo();

    }
    private void showinfo()
    {
        new Thread(){
            @Override
            public void run() {
                boolean flag=false;
                String url=HttpUtils.BASE_URL+"/student";
                Map<String,String> params=new HashMap<String,String>();
                params.put("uname",uname);
                String result=HttpUtils.getContextByHttp(url,params);
                if("".equals(result)||result==null)
                {
                    flag=false;
                }else
                {
                    try {
                        JSONObject json=new JSONObject(result);
                        Integer code=json.getInt("code");
                        if(code==1)
                        {
                            s.setUname(json.getString("uname"));
                            s.setPwd(json.getString("pwd"));
                            s.setName(json.getString("name"));
                            s.setGender(json.getString("gender"));
                            s.setDep(json.getString("dep"));
                            s.setInstitute(json.getString("institute"));
                            s.setMath(Double.parseDouble(json.getString("math")));
                            s.setChinese(Double.parseDouble(json.getString("chinese")));
                            s.setEnglish(Double.parseDouble(json.getString("english")));
                            flag=true;
                        }else if (code==0)
                        {
                            flag=false;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Message msg=new Message();
                msg.what=0x17;
                Bundle bundle=new Bundle();
                bundle.putBoolean("flag",flag);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
            Handler handler=new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    if(msg.what==0x17)
                    {
                       tv_infoSetTex(msg.getData().getBoolean("flag"));
                    }
                }
            };
        }.start();
    }
    public void tv_infoSetTex(boolean flag)
    {
        if(flag)
        {
            StringBuilder str=new StringBuilder();
            str.append("ID号码:"+s.getUname());
            str.append("\n");
            str.append("姓名:"+s.getName());
            str.append("\n");
            str.append("性别:"+s.getGender());
            str.append("\n");
            str.append("学院:"+s.getInstitute());
            str.append("\n");
            str.append("专业:"+s.getDep());
            str.append("\n");
            str.append("数学:"+s.getMath());
            str.append("\n");
            str.append("语文:"+s.getChinese());
            str.append("\n");
            str.append("英语:"+s.getEnglish());
            String value=str.toString();
            tv_info.setText(value);
        }else
        {
            tv_info.setText("");
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_stuedit:
                StuEditDialogFragment edft=new StuEditDialogFragment();
                FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                edft.show(ft,"stuEditDialogFragment");
                break;
        }
    }
}
