package com.cqju.studentsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 学生列表适配器
 */
public class StudnetAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    public StudnetAdapter(Context context)
    {
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return AdminActivity.list.size();
    }

    @Override
    public Object getItem(int position) {
        return AdminActivity.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder
    {
        public TextView content;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null)
        {
            holder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.itemlis,null);
            holder.content=(TextView)convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        }else
        {
            holder=(ViewHolder)convertView.getTag();
        }
        holder.content.setText(AdminActivity.list.get(position).toString());
        return convertView;
    }
}
