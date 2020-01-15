package com.cqju.studentsystem;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * 显示列表元素的详细信息的窗口
 */
public class infoDialogFragment extends DialogFragment {
    private View view;
    private TextView tv_info;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCancelable(true);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view=inflater.inflate(R.layout.info_dialog,container,false);
        tv_info=(TextView)view.findViewById(R.id.tv_info);
        Bundle bundle = getArguments();
        if(bundle!=null)
        {
            tv_info.setText(bundle.getString("info"));
        }else
            tv_info.setText("");
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Dialog dialog=getDialog();
        if(dialog!=null)
        {
            Window window=dialog.getWindow();
            DisplayMetrics dm=new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);//获取手机屏幕大小
            //宽度按屏幕宽比例缩小，高度自动对齐
            window.setLayout((int) (dm.widthPixels * 0.9), -2);
            // 位置位于中间
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            window.setAttributes(wlp);
        }
    }
}
