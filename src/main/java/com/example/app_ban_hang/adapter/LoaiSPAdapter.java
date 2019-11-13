package com.example.app_ban_hang.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_ban_hang.R;
import com.example.app_ban_hang.model.LoaiSP;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiSPAdapter extends BaseAdapter {
    Context context;

    ArrayList<LoaiSP> loaiSPArrayList ;

    public LoaiSPAdapter(Context context, ArrayList<LoaiSP> loaiSPArrayList) {
        this.context = context;
        this.loaiSPArrayList = loaiSPArrayList;
    }

    @Override
    public int getCount() {
        return loaiSPArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return loaiSPArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public  class Viewholder{
        TextView txtLoaiSP;
        ImageView imgLoaiSP;



    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder viewholder ;
        if(convertView == null)
        {
            viewholder = new Viewholder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.dong_listview_loaisp, null);
            viewholder.txtLoaiSP = (TextView) convertView.findViewById(R.id.txtLoaiSP);
            viewholder.imgLoaiSP = (ImageView) convertView.findViewById(R.id.imgLoaiSP);
            convertView.setTag(viewholder);
        }else{
            viewholder = (Viewholder) convertView.getTag();


        }
        LoaiSP loaiSP = (LoaiSP) getItem(position);

        viewholder.txtLoaiSP.setText(loaiSP.getTenSP());

        // dùng picasso đọc đường dẫn URL từ internet
        Picasso.with(context).load(loaiSP.getHinhanhloaiSP())
                .placeholder(R.drawable.noimage)// time đợi loada ảnh về nó sẽ hiển thị ảnh
                .error(R.drawable.imgerror)// load lỗi ảnh sẽ hiện thị
                .into(viewholder.imgLoaiSP);





        return convertView;
    }
}
