package com.cqju.studentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员操作界面
 */
public class AdminActivity extends AppCompatActivity implements View.OnClickListener, AddStudentDialogFragment.CallBackValue, AdapterView.OnItemClickListener,SwitchDialogFragment.onButtonClickListener {
    private TextView tv_title;
    private Button btn_add;
    private Button btn_search;
    private ListView lv_stu;
    private EditText edt_search;
    public static List<Student> list;
    SwitchDialogFragment sdft;
    FragmentTransaction ft;
    StudnetAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        list= new ArrayList<Student>();
        tv_title=(TextView)findViewById(R.id.tv_title);
        lv_stu=(ListView)findViewById(R.id.lv_stu);
        edt_search=(EditText)findViewById(R.id.edit_search);
        btn_search=(Button)findViewById(R.id.btn_search);
        Bundle bundle=getIntent().getExtras();
        tv_title.setText(bundle.getString("uname")+"在线");
        btn_add=(Button)findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        btn_search.setOnClickListener(this);

        initiallist();
        adapter=new StudnetAdapter(this);
        lv_stu.setAdapter(adapter);

        lv_stu.setOnItemClickListener(this);
    }
    private List<Student> jsonarrytolsit(JSONArray jsonArray)
    {
        List<Student> temp=new ArrayList<Student>();
        Student s=null;
        for(int i=0;i<jsonArray.length();i++)
        {
            JSONObject object= null;
            try {
                object = jsonArray.getJSONObject(i);
                s=new Student();
                s.setUname(object.getString("uname"));
                s.setPwd( object.getString("pwd"));
                s.setName(object.getString("name"));
                s.setGender(object.getString("gender"));
                s.setDep(object.getString("dep"));
                s.setInstitute(object.getString("institute"));
                s.setMath(Double.parseDouble( object.getString("math")));
                s.setEnglish(Double.parseDouble(object.getString("chinese")));
                s.setChinese( Double.parseDouble(object.getString("english")));
                temp.add(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return temp;

    }
    private void initiallist()
    {
        new Thread(){
            @Override
            public void run() {
                String url = HttpUtils.BASE_URL + "/select";
                Map<String, String> params = new HashMap<String, String>();
                params.put("key","");
                String result=HttpUtils.getContextByHttp(url,params);
                if(!"".equals(result)||result!=null)
                {
                    try {
                        JSONObject json=new JSONObject(result);
                        JSONArray  jsonArray=json.getJSONArray("students");
                        if(jsonArray.length()>0)
                        {
                            list=jsonarrytolsit(jsonArray);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }
    private void search()
    {
        final String key=edt_search.getText().toString().trim();
        new Thread() {
            @Override
            public void run() {
                boolean flag=true;
                String url = HttpUtils.BASE_URL + "/select";
                Map<String, String> params = new HashMap<String, String>();
                params.put("key", key);
                String result = HttpUtils.getContextByHttp(url, params);
                if (!"".equals(result) || result != null) {
                    try {
                        JSONObject json = new JSONObject(result);
                        JSONArray jsonArray = json.getJSONArray("students");
                        list = jsonarrytolsit(jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Message msg=new Message();
                    msg.what=0x14;
                    Bundle data=new Bundle();
                    data.putBoolean("flag",flag);
                    msg.setData(data);
                    handler.sendMessage(msg);
                }
            }
            Handler handler=new Handler(){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    if(msg.what==0x14) {
                        boolean flag = msg.getData().getBoolean("flag");
                        if (flag) {
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            };
        }.start();
    }
    private void del(String uname)
    {
        final String key=uname;
        new Thread(){
            @Override
            public void run() {
                boolean flag=false;
                String url = HttpUtils.BASE_URL + "/delete";
                Map<String,String> params=new HashMap<String,String>();
                params.put("uname",key);
                String result=HttpUtils.getContextByHttp(url,params);
                if("".equals(result)||result==null)
                {
                    flag=false;
                }else
                {
                    try {
                        JSONObject json=new JSONObject(result);
                        Integer code=json.getInt("code");
                        if(code!=0)
                        {
                            flag=true;
                        }else
                        {
                            flag=false;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Message msg=new Message();
                msg.what=0x15;
                Bundle bundle=new Bundle();
                bundle.putBoolean("flag",flag);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
            Handler handler=new Handler(){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    if(msg.what==0x15)
                    {
                        Boolean flag=msg.getData().getBoolean("flag");
                        if(flag)
                        {
                            Toast.makeText(AdminActivity.this,"删除成功",Toast.LENGTH_LONG).show();
                        }else
                        {
                            Toast.makeText(AdminActivity.this,"删除失败",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            };
        }.start();
    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId())
        {
            case R.id.btn_add:
                AddStudentDialogFragment dft=new AddStudentDialogFragment();//添加学生弹窗
                ft = getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                dft.show(ft,"AddDialogFrament");
                break;
            case R.id.btn_search:
                search();
                break;
        }
    }

    @Override
    public void sendMessage(Bundle data,Student s) {
        list.add(s);
        adapter.notifyDataSetChanged();
    }

    private  int position;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.position=position;
        sdft=new SwitchDialogFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        sdft.show(ft,"SwitchesDialogFrament");
    }

    public void ListReflesh()
    {
        adapter.notifyDataSetChanged();
    }
    @Override
    public void myClick(View v) {
        Bundle bundle=null;
        switch (v.getId())
        {
            case R.id.btn_info:
                Student s=list.get(position);
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
                bundle=new Bundle();
                bundle.putString("info",str.toString());
                infoDialogFragment ift=new infoDialogFragment();
                ift.setArguments(bundle);
                ft = getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                sdft.getDialog().dismiss();
                ift.show(ft,"infoDialogFrament");
                break;
            case R.id.btn_edit:
                bundle=new Bundle();
                bundle.putInt("position",position);
                editDialogFragment eft=new editDialogFragment();
                eft.setArguments(bundle);
                ft = getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                sdft.getDialog().dismiss();
                eft.show(ft,"editDialogFrament");
                break;
            case R.id.btn_del:
                del(list.get(position).getUname());//从数据库中删除
                list.remove(position);
                adapter.notifyDataSetChanged();
                sdft.getDialog().dismiss();
                break;
        }
    }
}
