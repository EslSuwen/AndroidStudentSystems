package com.cqju.studentsystem;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/***
 * 列表元素被点击弹出的窗口
 */
public class SwitchDialogFragment extends DialogFragment implements View.OnClickListener {
    private View view;
    private Button btn_info;
    private Button btn_edit;
    private Button btn_del;
    private onButtonClickListener click;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCancelable(true);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view=inflater.inflate(R.layout.switchlist_dialog,container,false);
        btn_info=(Button)view.findViewById(R.id.btn_info);
        btn_edit=(Button)view.findViewById(R.id.btn_edit);
        btn_del=(Button)view.findViewById(R.id.btn_del);
        btn_info.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_edit.setOnClickListener(this);
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
            window.setLayout((int) (dm.widthPixels * 0.8), -2);
            // 设置中间对齐
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            window.setAttributes(wlp);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        click=(onButtonClickListener)getActivity();
    }

    @Override
    public void onClick(View v) {
        click.myClick(v);
    }

    public interface onButtonClickListener
    {
        public void myClick(View v);
    }
}
