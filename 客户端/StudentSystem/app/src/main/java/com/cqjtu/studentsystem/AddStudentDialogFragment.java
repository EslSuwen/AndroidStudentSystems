package com.cqju.studentsystem;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/***
 * 管理员添加新的学生信息；
 */
public class AddStudentDialogFragment extends DialogFragment implements View.OnClickListener {
    private View view;
    private EditText edt_uname;
    private EditText edt_pwd;
    private EditText edt_name;
    private EditText edt_sex;
    private EditText edt_institute;
    private EditText edt_dep;
    private EditText edt_math;
    private EditText edt_chinese;
    private EditText edt_english;
    private Button btn_add;
    private Bundle data;
    private CallBackValue callBackValue;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(true);//点击Dialog屏幕外的区域会dismiss
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//去标题
        view=inflater.inflate(R.layout.student_dialog,container,false);
        edt_uname=(EditText)view.findViewById(R.id.edt_uname);
        edt_pwd=(EditText)view.findViewById(R.id.edt_pwd);
        edt_name=(EditText)view.findViewById(R.id.edt_name);
        edt_sex=(EditText)view.findViewById(R.id.edt_gendr);
        edt_institute=(EditText)view.findViewById(R.id.edt_institute);
        edt_dep=(EditText)view.findViewById(R.id.edt_dep);
        edt_math=(EditText)view.findViewById(R.id.edt_math);
        edt_chinese=(EditText)view.findViewById(R.id.edt_chineses);
        edt_english=(EditText)view.findViewById(R.id.edt_english);
        btn_add=(Button)view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        edt_math.setRawInputType(Configuration.KEYBOARD_QWERTY);// 设置文本框获得焦点时弹出数字输入键盘
        edt_chinese.setRawInputType(Configuration.KEYBOARD_QWERTY);// 设置文本框获得焦点时弹出数字输入键盘
        edt_english.setRawInputType(Configuration.KEYBOARD_QWERTY); // 设置文本框获得焦点时弹出数字输入键盘
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = getDialog().getWindow();
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            //宽度按屏幕宽比例缩小，高度自动对齐
            window.setLayout((int) (dm.widthPixels * 0.9), -2);
            // 设置靠近头部
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.TOP;
            wlp.y = 80;//具体头部距离
            window.setAttributes(wlp);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //activity重写了回调接口  得到接口的实例化对象
        callBackValue =(CallBackValue) getActivity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_add:
                if(edt_uname.getText().toString().trim().equals(""))
                {
                    Toast.makeText(getActivity(),"ID不能为空",Toast.LENGTH_LONG).show();
                }else if(edt_pwd.getText().toString().trim().equals(""))
                {
                    Toast.makeText(getActivity(),"密码不能为空",Toast.LENGTH_LONG).show();
                }else if(edt_name.getText().toString().trim().equals(""))
                {
                    Toast.makeText(getActivity(),"姓名不能为空",Toast.LENGTH_LONG).show();
                }else if(Double.parseDouble(edt_math.getText().toString().trim())>150||Double.parseDouble(edt_math.getText().toString().trim())<0)
                {
                    Toast.makeText(getActivity(),"数学分数输入错误",Toast.LENGTH_LONG).show();
                }else if(Double.parseDouble(edt_chinese.getText().toString().trim())>150||Double.parseDouble(edt_chinese.getText().toString().trim())<0)
                {
                    Toast.makeText(getActivity(),"语文分数输入错误",Toast.LENGTH_LONG).show();
                }else if(Double.parseDouble(edt_english.getText().toString().trim())>150||Double.parseDouble(edt_english.getText().toString().trim())<0)
                {
                    Toast.makeText(getActivity(),"英语分数输入错误",Toast.LENGTH_LONG).show();
                }else{
                   Student s=new Student();
                    s.setUname(edt_uname.getText().toString().trim());
                    s.setPwd(edt_pwd.getText().toString().trim());
                    s.setName(edt_name.getText().toString().trim());
                    s.setGender(edt_sex.getText().toString().trim());
                    s.setInstitute(edt_institute.getText().toString().trim());
                    s.setDep(edt_dep.getText().toString().trim());
                    s.setMath(Double.parseDouble(edt_math.getText().toString()));
                    s.setChinese(Double.parseDouble(edt_chinese.getText().toString()));
                    s.setEnglish(Double.parseDouble(edt_english.getText().toString()));
                    insertstudent(s);
                }

                break;
        }
    }
    public interface CallBackValue{
        public void  sendMessage(Bundle data,Student s);
    }
    private Student newone;
    private void insertstudent(Student s)
    {
        newone=s;
        new Thread(){
            @Override
            public void run() {
                boolean flag=false;
                String url = HttpUtils.BASE_URL + "/insert";
                Map<String,String> params=new HashMap<String,String>();
                params.put("uname",newone.getUname());
                params.put("pwd",newone.getPwd());
                params.put("name",newone.getName());
                params.put("sex",newone.getGender());
                params.put("institute",newone.getInstitute());
                params.put("dep",newone.getDep());
                params.put("math",newone.getMath()+"");
                params.put("chinese",newone.getChinese()+"");
                params.put("english",newone.getEnglish()+"");
                String result = HttpUtils.getContextByHttp(url, params);
                if("".equals(result.trim())||result==null) {
                    flag=false;
                }else {
                    JSONObject json = null;
                    try {
                        json = new JSONObject(result);
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
                msg.what=0x13;
                Bundle bundle=new Bundle();
                bundle.putBoolean("flag",flag);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
            Handler handler=new Handler(){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    boolean regflag;
                    if(msg.what==0x13)
                    {
                        Bundle bundle=msg.getData();
                        regflag=bundle.getBoolean("flag");
                        if (regflag) {
                            callBackValue.sendMessage(data,newone);
                            Toast.makeText(getActivity(), "添加成功", Toast.LENGTH_LONG).show();
                            getDialog().dismiss();
                        } else {
                            Toast.makeText(getActivity(), "添加失败", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            };
        }.start();

        return ;
    }
}
