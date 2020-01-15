package com.cqju.studentsystem;

import android.app.Dialog;
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

/**
 * 管理员修改列表元素信息的窗口
 */
public class editDialogFragment extends DialogFragment implements View.OnClickListener {
    private View view;
    private int position;
    private Button btn_save;
    private EditText edt_uname;
    private EditText edt_pwd;
    private EditText edt_name;
    private EditText edt_gender;
    private EditText edt_dep;
    private EditText edt_institute;
    private EditText edt_math;
    private EditText edt_chinese;
    private EditText edt_english;
    Map<String,String> params;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCancelable(true);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view=inflater.inflate(R.layout.edit_dialog,container,false);
        btn_save=(Button)view.findViewById(R.id.btn_save);
        edt_uname=(EditText)view.findViewById(R.id.edt_uname);
        edt_pwd=(EditText)view.findViewById(R.id.edt_pwd);
        edt_name=(EditText)view.findViewById(R.id.edt_name);
        edt_gender=(EditText)view.findViewById(R.id.edt_gendr);
        edt_dep=(EditText)view.findViewById(R.id.edt_dep);
        edt_institute=(EditText)view.findViewById(R.id.edt_institute);
        edt_math=(EditText)view.findViewById(R.id.edt_math);
        edt_chinese=(EditText)view.findViewById(R.id.edt_chineses);
        edt_english=(EditText)view.findViewById(R.id.edt_english);
        btn_save.setOnClickListener(this);

        position=getArguments().getInt("position");
        edt_uname.setText(AdminActivity.list.get(position).getUname());
        edt_pwd.setText(AdminActivity.list.get(position).getPwd());
        edt_name.setText(AdminActivity.list.get(position).getName());
        edt_gender.setText(AdminActivity.list.get(position).getGender());
        edt_dep.setText(AdminActivity.list.get(position).getDep());
        edt_institute.setText(AdminActivity.list.get(position).getInstitute());
        edt_math.setText(AdminActivity.list.get(position).getMath()+"");
        edt_chinese.setText(AdminActivity.list.get(position).getChinese()+"");
        edt_english.setText(AdminActivity.list.get(position).getEnglish()+"");
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
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_save:
                if("".equals(edt_uname.getText().toString().trim()))
                {
                    Toast.makeText(getActivity(),"ID号码不能为空",Toast.LENGTH_LONG).show();
                }else if ("".equals(edt_pwd.getText().toString().trim()))
                {
                    Toast.makeText(getActivity(),"密码不能为空",Toast.LENGTH_LONG).show();
                }else if ("".equals(edt_name.getText().toString().trim()))
                {
                    Toast.makeText(getActivity(),"姓名不能为空",Toast.LENGTH_LONG).show();
                }else if (Double.parseDouble(edt_math.getText().toString().trim())>150||Double.parseDouble(edt_math.getText().toString().trim())<0)
                {
                    Toast.makeText(getActivity(),"数学分数输入有误",Toast.LENGTH_LONG).show();
                }else if (Double.parseDouble(edt_chinese.getText().toString().trim())>150||Double.parseDouble(edt_chinese.getText().toString().trim())<0)
                {
                    Toast.makeText(getActivity(),"语文分数输入有误",Toast.LENGTH_LONG).show();
                }else if (Double.parseDouble(edt_english.getText().toString().trim())>150||Double.parseDouble(edt_english.getText().toString().trim())<0)
                {
                    Toast.makeText(getActivity(),"英语分数输入有误",Toast.LENGTH_LONG).show();
                }else {
                    params = new HashMap<String, String>();
                    params.put("switches", "admin");
                    params.put("olduname", AdminActivity.list.get(position).getUname());
                    params.put("uname", edt_uname.getText().toString().trim());
                    params.put("pwd", edt_pwd.getText().toString().trim());
                    params.put("name", edt_name.getText().toString().trim());
                    params.put("gender", edt_gender.getText().toString().trim());
                    params.put("dep", edt_dep.getText().toString().trim());
                    params.put("institute", edt_institute.getText().toString().trim());
                    params.put("math", edt_math.getText().toString().trim());
                    params.put("chinese", edt_chinese.getText().toString().trim());
                    params.put("english", edt_english.getText().toString().trim());
                    editInfo();
                }
                break;
        }
    }
    private void editInfo()
    {
        new Thread(){
            @Override
            public void run() {
                boolean flag=false;
                String url=HttpUtils.BASE_URL+"/edit";
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
                msg.what=0x16;
                Bundle bundle=new Bundle();
                bundle.putBoolean("flag",flag);
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
            Handler handler=new Handler(){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    if(msg.what==0x16)
                    {
                        if(msg.getData().getBoolean("flag"))
                        {
                            Toast.makeText(getActivity(),"修改成功",Toast.LENGTH_LONG).show();
                            AdminActivity.list.get(position).setUname(edt_uname.getText().toString().trim());
                            AdminActivity.list.get(position).setPwd(edt_pwd.getText().toString().trim());
                            AdminActivity.list.get(position).setName(edt_name.getText().toString().trim());
                            AdminActivity.list.get(position).setGender(edt_gender.getText().toString().trim());
                            AdminActivity.list.get(position).setDep(edt_dep.getText().toString().trim());
                            AdminActivity.list.get(position).setInstitute(edt_institute.getText().toString().trim());
                            AdminActivity.list.get(position).setMath(Double.parseDouble(edt_math.getText().toString().trim()));
                            AdminActivity.list.get(position).setChinese(Double.parseDouble(edt_chinese.getText().toString().trim()));
                            AdminActivity.list.get(position).setEnglish(Double.parseDouble(edt_english.getText().toString().trim()));
                            ((AdminActivity)getActivity()).ListReflesh();
                            getDialog().dismiss();
                        }else {
                            Toast.makeText(getActivity(),"未做任何修改",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            };
        }.start();
    }
}
