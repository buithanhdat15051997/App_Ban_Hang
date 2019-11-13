package com.example.app_ban_hang.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.app_ban_hang.R;
import com.example.app_ban_hang.model.SanPhamMoiNhat;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DienThoaiAdapter extends BaseAdapter {
    Context contextDT;
    ArrayList<SanPhamMoiNhat> arrayList;

    public DienThoaiAdapter(Context contextDT, ArrayList<SanPhamMoiNhat> arrayList) {
        this.contextDT = contextDT;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
   private  class viewholder{
        ImageView imgPhone;
        TextView txttenPhone, txtGiaPhone, txtmotaPhone;


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewholder  viewholder = null;
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) contextDT.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.dong_dienthoai_listview,null);
            viewholder = new viewholder();
            viewholder.imgPhone = (ImageView) convertView.findViewById(R.id.imgdienthoai);
            viewholder.txttenPhone = (TextView)convertView.findViewById(R.id.txttendienthoai);
            viewholder.txtmotaPhone = (TextView)convertView.findViewById(R.id.txtmotadt);
            viewholder.txtGiaPhone = (TextView)convertView.findViewById(R.id.txtgiadienthoai);
            convertView.setTag(viewholder);
        }else{

            viewholder = (DienThoaiAdapter.viewholder) convertView.getTag();

        }
        SanPhamMoiNhat sanPhamMoiNhat = arrayList.get(position);

        viewholder.txttenPhone.setText(sanPhamMoiNhat.getTenSPNew());

        viewholder.txtmotaPhone.setMaxLines(2);// sét mô tả thành hai dòng
        viewholder.txtmotaPhone.setEllipsize(TextUtils.TruncateAt.END);// sét cắt dấu 3 chấm
        viewholder.txtmotaPhone.setText(sanPhamMoiNhat.getMotaSPNew());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewholder.txtGiaPhone.setText("Giá: "+decimalFormat.format(sanPhamMoiNhat.getGiaSPNew())+" Đồng");
   // gán giá trị cho sản phẩm
        Picasso.with(contextDT).load(sanPhamMoiNhat.getHinhanhSPNew()).placeholder(R.drawable.noimage).error(R.drawable.imgerror)
                .into(viewholder.imgPhone);


        return convertView;
    }
}
