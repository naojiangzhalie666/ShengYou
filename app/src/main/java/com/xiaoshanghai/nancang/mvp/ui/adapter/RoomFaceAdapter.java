package com.xiaoshanghai.nancang.mvp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoshanghai.nancang.R;
import com.xiaoshanghai.nancang.net.bean.RoomFace;

import java.util.List;

public class RoomFaceAdapter extends BaseAdapter {
    private List<RoomFace> list;
    private Context mContext;

    public RoomFaceAdapter(List<RoomFace> list, Context mContext) {
        super();
        this.list = list;
        this.mContext = mContext;
    }


    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        RoomFace emoji = list.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_grid_face, null);
            holder.iv = convertView.findViewById(R.id.face_image);
            holder.mTvFaceName = convertView.findViewById(R.id.tv_face_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (emoji != null) {
            holder.iv.setImageBitmap(emoji.getIcon());
            holder.mTvFaceName.setText(emoji.getFaceName());
        }
        return convertView;
    }

    class ViewHolder {
        ImageView iv;
        TextView mTvFaceName;
    }
}

