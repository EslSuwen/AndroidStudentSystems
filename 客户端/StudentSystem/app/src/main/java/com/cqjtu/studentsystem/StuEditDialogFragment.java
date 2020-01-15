package com.cqju.studentsystem;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
 * 学生修改个人信息的界面
 */
public class StuEditDialogFragment extends DialogFragment implements View.OnClickListener {
    private View view;
    private EditText edt_name;
    private EditText edt_gender;
    private EditText edt_dep;
    private EditText edt_institute;
    private Button  btn_save;
    private Map<String,String> params;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCancelable(true);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view=inflater.inflate(R.layout.stuedit_dialog,container,false);
        edt_name=(EditText)view.findViewById(R.id.edt_name);
        edt_gender=(EditText)view.findViewById(R.id.edt_gendr);
        edt_dep=(EditText)view.findViewById(R.id.edt_dep);
        edt_institute=(EditText)view.findViewById(R.id.edt_institute);
        btn_save=(Button)view.findViewById(R.id.btn_save);

        btn_save.setOnClickListener(this);
        edt_name.setText(StudentActivity.s.getName());
        edt_gender.setText(StudentActivity.s.getGender());
        edt_dep.setText(StudentActivity.s.getDep());
        edt_institute.setText(StudentActivity.s.getInstitute());
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_save:
                if("".equals(edt_name.getText().toString().trim()))
                {
                    Toast.makeText(getActivity(),"姓名不能为空",Toast.LENGTH_LONG).show();
                }else
                {
                    params=new HashMap<String,String>();
                    params.put("switches","student");
                    params.put("uname",StudentActivity.s.getUname());
                    params.put("name",edt_name.getText().toString().trim());
                    params.put("gender",edt_gender.getText().toString().trim());
                    params.put("dep",edt_dep.getText().toString().trim());
                    params.put("institute",edt_institute.getText().toString().trim());
                    editstudent();
                }
                break;
        }
    }
    private void editstudent()
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
                        Boolean flag=msg.getData().getBoolean("flag");
                        if(flag)
                        {
                            Toast.makeText(getActivity(),"修改成功",Toast.LENGTH_LONG).show();
                            StudentActivity.s.setName(edt_name.getText().toString().trim());
                            StudentActivity.s.setGender(edt_gender.getText().toString().trim());
                            StudentActivity.s.setDep(edt_dep.getText().toString().trim());
                            StudentActivity.s.setInstitute(edt_institute.getText().toString().trim());
                            ((StudentActivity)getActivity()).tv_infoSetTex(flag);
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
